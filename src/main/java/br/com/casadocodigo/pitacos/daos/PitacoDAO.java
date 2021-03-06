package br.com.casadocodigo.pitacos.daos;

import java.util.List;

import org.springframework.data.repository.Repository;

import br.com.casadocodigo.pitacos.models.Pitaco;

public interface PitacoDAO extends Repository<Pitaco, Integer> {

	Pitaco findOne(Integer id);

	void save(Pitaco pitaco);

	List<Pitaco> findAllByUsuarioId(Integer id);

}
