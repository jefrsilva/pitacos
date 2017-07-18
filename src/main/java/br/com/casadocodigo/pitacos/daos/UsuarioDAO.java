package br.com.casadocodigo.pitacos.daos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.casadocodigo.pitacos.models.Usuario;

public interface UsuarioDAO extends Repository<Usuario, Integer>, UserDetailsService {

	@Override
	@Query("select u from Usuario u where email=:email")
	UserDetails loadUserByUsername(@Param("email") String email) throws UsernameNotFoundException;

	Usuario findOne(Integer id);
	
	void save(Usuario usuario);

}
