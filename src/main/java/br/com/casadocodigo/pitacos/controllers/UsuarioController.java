package br.com.casadocodigo.pitacos.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.casadocodigo.pitacos.daos.PitacoDAO;
import br.com.casadocodigo.pitacos.daos.UsuarioDAO;
import br.com.casadocodigo.pitacos.dtos.PitacoDTO;
import br.com.casadocodigo.pitacos.dtos.UsuarioDTO;
import br.com.casadocodigo.pitacos.models.Usuario;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioDAO usuarioDAO;

	@Autowired
	private PitacoDAO pitacoDAO;

	@PostMapping
	public ResponseEntity<?> cadastra(@RequestBody UsuarioDTO usuarioDTO) {
		Usuario usuario = usuarioDTO.toUsuario();
		usuarioDAO.save(usuario);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuario.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}

	@GetMapping("/{usuarioId}/pitacos")
	public List<PitacoDTO> buscaPitacos(@PathVariable Integer usuarioId) {
		return pitacoDAO.findAllByUsuarioId(usuarioId).stream()
				.map(pitaco -> new PitacoDTO(pitaco))
				.collect(Collectors.toList());
	}

}
