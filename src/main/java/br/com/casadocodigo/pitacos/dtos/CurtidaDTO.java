package br.com.casadocodigo.pitacos.dtos;

import br.com.casadocodigo.pitacos.models.Curtida;

public class CurtidaDTO {

	private Integer id;

	private Integer pitacoId;

	private Integer usuarioId;

	public CurtidaDTO() {

	}

	public CurtidaDTO(Curtida curtida) {
		this.id = curtida.getId();
		this.pitacoId = curtida.getPitaco().getId();
		this.usuarioId = curtida.getUsuario().getId();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPitacoId() {
		return pitacoId;
	}

	public void setPitacoId(Integer pitacoId) {
		this.pitacoId = pitacoId;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

}
