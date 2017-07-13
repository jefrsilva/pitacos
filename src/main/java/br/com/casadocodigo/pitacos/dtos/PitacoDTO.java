package br.com.casadocodigo.pitacos.dtos;

import java.time.LocalDateTime;

import br.com.casadocodigo.pitacos.models.Pitaco;
import br.com.casadocodigo.pitacos.models.Usuario;

public class PitacoDTO {

	private Integer id;

	private String texto;

	private LocalDateTime data;

	private Integer idDoUsuario;

	public PitacoDTO() {
		
	}
	
	public PitacoDTO(Pitaco pitaco) {
		this.id = pitaco.getId();
		this.texto = pitaco.getTexto();
		this.data = pitaco.getData();
		this.idDoUsuario = pitaco.getUsuario().getId();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public Integer getIdDoUsuario() {
		return idDoUsuario;
	}

	public void setIdDoUsuario(Integer idDoUsuario) {
		this.idDoUsuario = idDoUsuario;
	}

	public Pitaco toPitaco(Usuario usuario) {
		Pitaco pitaco = new Pitaco();
		pitaco.setTexto(texto);
		pitaco.setUsuario(usuario);
		return pitaco;
	}

}
