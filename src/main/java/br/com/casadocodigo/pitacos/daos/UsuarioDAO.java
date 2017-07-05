package br.com.casadocodigo.pitacos.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import br.com.casadocodigo.pitacos.models.Usuario;

@Repository
public class UsuarioDAO implements UserDetailsService {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		TypedQuery<Usuario> query = manager.createQuery("select u from Usuario u where email=:email", Usuario.class);
		query.setParameter("email", email);
		List<Usuario> resultado = query.getResultList();
		if (resultado.isEmpty()) {
			throw new UsernameNotFoundException("Usuario não encontrado!");
		}
		return resultado.get(0);
	}

}
