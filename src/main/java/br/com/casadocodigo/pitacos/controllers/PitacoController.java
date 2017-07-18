package br.com.casadocodigo.pitacos.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.casadocodigo.pitacos.daos.CurtidaDAO;
import br.com.casadocodigo.pitacos.daos.PitacoDAO;
import br.com.casadocodigo.pitacos.daos.UsuarioDAO;
import br.com.casadocodigo.pitacos.dtos.CurtidaDTO;
import br.com.casadocodigo.pitacos.dtos.PitacoDTO;
import br.com.casadocodigo.pitacos.models.Curtida;
import br.com.casadocodigo.pitacos.models.Pitaco;
import br.com.casadocodigo.pitacos.models.Usuario;

@RestController
@RequestMapping("/api/v1/pitacos")
public class PitacoController {

	@Autowired
	private PitacoDAO pitacoDAO;

	@Autowired
	private UsuarioDAO usuarioDAO;

	@Autowired
	private CurtidaDAO curtidaDAO;

	@GetMapping("/{id}")
	public PitacoDTO busca(@PathVariable Integer id) {
		return new PitacoDTO(pitacoDAO.findOne(id));
	}

	@PostMapping
	public ResponseEntity<?> adiciona(@RequestBody PitacoDTO pitacoDTO, @AuthenticationPrincipal Usuario usuario) {
		Pitaco pitaco = pitacoDTO.toPitaco(usuario);
		pitacoDAO.save(pitaco);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pitaco.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/{pitacoId}/curtidas/{curtidaId}")
	public ResponseEntity<?> removerCurtida(@PathVariable Integer pitacoId, @PathVariable Integer curtidaId,
			@AuthenticationPrincipal Usuario usuario) {
		Curtida curtida = curtidaDAO.findOne(curtidaId);
		if (curtida == null) {
			return ResponseEntity.notFound().build();
		}

		if (curtida.getUsuario().getId() == usuario.getId()) {
			List<Curtida> removidas = curtidaDAO.deleteById(curtidaId);
			if (!removidas.isEmpty()) {
				return ResponseEntity.ok(new CurtidaDTO(removidas.get(0)));
			} else {
				return ResponseEntity.notFound().build();
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@PostMapping("/{pitacoId}/curtidas")
	public ResponseEntity<?> curtir(@PathVariable Integer pitacoId, @AuthenticationPrincipal Usuario usuario) {
		Curtida curtida = curtidaDAO.findOneByPitacoIdAndUsuarioId(pitacoId, usuario.getId());
		if (curtida != null) {
			return ResponseEntity.status(HttpStatus.GONE).build();
		}

		Pitaco pitaco = pitacoDAO.findOne(pitacoId);
		usuario = usuarioDAO.findOne(usuario.getId());

		Curtida novaCurtida = new Curtida(pitaco, usuario);
		curtidaDAO.save(novaCurtida);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{pitacoId}/curtidas/{curtidaId}")
				.buildAndExpand(pitaco.getId(), novaCurtida.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

}
