package br.com.casadocodigo.pitacos.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.casadocodigo.pitacos.daos.CurtidaDAO;
import br.com.casadocodigo.pitacos.dtos.CurtidaDTO;

@RestController
@RequestMapping("/api/v1/curtidas")
public class CurtidaController {

	@Autowired
	private CurtidaDAO curtidaDAO;

	@GetMapping("/{id}")
	public List<CurtidaDTO> buscaTodas(@PathVariable Integer id) {
		return curtidaDAO.findAllByPitacoId(id).stream()
			.map(curtida -> new CurtidaDTO(curtida))
			.collect(Collectors.toList());
	}

}
