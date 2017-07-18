package br.com.casadocodigo.pitacos.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Curtida {

	@Id
	@GeneratedValue
	private Integer id;

	@OneToOne(cascade = CascadeType.PERSIST, optional = false)
	private Pitaco pitaco;

	@OneToOne(cascade = CascadeType.PERSIST, optional = false)
	private Usuario usuario;
	
	public Curtida() {
		
	}

	public Curtida(Pitaco pitaco, Usuario usuario) {
		this.pitaco = pitaco;
		this.usuario = usuario;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Pitaco getPitaco() {
		return pitaco;
	}

	public void setPitaco(Pitaco pitaco) {
		this.pitaco = pitaco;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
