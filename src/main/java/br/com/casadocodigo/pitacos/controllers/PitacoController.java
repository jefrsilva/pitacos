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
import br.com.casadocodigo.pitacos.dtos.PitacoDTO;
import br.com.casadocodigo.pitacos.models.Pitaco;

@RestController
@RequestMapping("/api/pitaco")
public class PitacoController {

	@Autowired
	private PitacoDAO pitacoDao;

	@GetMapping
	public List<PitacoDTO> buscaTodos() {
		return pitacoDao.todos().stream()
				.map(pitaco -> new PitacoDTO(pitaco))
				.collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public PitacoDTO busca(@PathVariable Integer id) {
		return new PitacoDTO(pitacoDao.busca(id));
	}

	@PostMapping
	public ResponseEntity<?> adiciona(@RequestBody Pitaco pitaco) {
		pitacoDao.adiciona(pitaco);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pitaco.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}

}
