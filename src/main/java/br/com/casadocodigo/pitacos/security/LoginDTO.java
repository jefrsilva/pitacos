package br.com.casadocodigo.pitacos.security;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class LoginDTO {

	@NotBlank
	@Email
	private String email;

	@NotBlank
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

	@Override
	public String toString() {
		return "LoginDTO [email=" + email + ", senha=" + senha + "]";
	}

}
