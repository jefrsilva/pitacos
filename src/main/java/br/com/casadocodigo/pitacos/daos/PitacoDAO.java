package br.com.casadocodigo.pitacos.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.com.casadocodigo.pitacos.models.Pitaco;

@Repository
@Transactional
public class PitacoDAO {

	@PersistenceContext
	private EntityManager manager;

	public List<Pitaco> todos() {
		return manager.createQuery("select p from Pitaco p order by data desc", Pitaco.class).getResultList();
	}

	public Pitaco busca(Integer id) {
		return manager.find(Pitaco.class, id);
	}

	public void adiciona(Pitaco pitaco) {
		manager.persist(pitaco);
	}

}
