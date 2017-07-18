package br.com.casadocodigo.pitacos.daos;

import java.util.List;

import org.springframework.data.repository.Repository;

import br.com.casadocodigo.pitacos.models.Curtida;

public interface CurtidaDAO extends Repository<Curtida, Integer> {

	Curtida findOne(Integer curtidaId);

	List<Curtida> findAllByPitacoId(Integer id);

	void save(Curtida curtida);
	
	List<Curtida> deleteById(Integer curtidaId);
	
	Curtida findOneByPitacoIdAndUsuarioId(Integer pitacoId, Integer usuarioId);

}
