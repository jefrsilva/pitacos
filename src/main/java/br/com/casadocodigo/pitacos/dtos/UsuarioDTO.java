package br.com.casadocodigo.pitacos.dtos;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.casadocodigo.pitacos.models.Usuario;

public class UsuarioDTO {

	private String email;

	private String senha;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Usuario toUsuario() {
		Usuario usuario = new Usuario();
		usuario.setEmail(email);
		usuario.setSenha(new BCryptPasswordEncoder().encode(senha));
		return usuario;
	}

}
