package br.com.casadocodigo.pitacos.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Usuario {

	@Id
	@GeneratedValue
	private Integer id;

	private String email;

	private String senha;

	@OneToMany(mappedBy = "usuario")
	private List<Pitaco> pitacos;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public List<Pitaco> getPitacos() {
		return pitacos;
	}

	public void setPitacos(List<Pitaco> pitacos) {
		this.pitacos = pitacos;
	}

}
