package com.practicasupervisada.guardia2.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Evento")
public class Evento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idEvento;
	private String descripcion;
	private Date fechaEvento;
	private String observacionDeGuardia;
	private Boolean ocurrencia;
	
	@ManyToOne
	@JoinColumn(name = "ID_UsuarioSector")
	private Usuario usuarioResponsableSector;
	
	@ManyToOne
	@JoinColumn(name = "ID_UsuarioGuardia")
	private Usuario usurioGuardia;
	
	public int getIdEvento() {
		return idEvento;
	}
	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getFechaEvento() {
		return fechaEvento;
	}
	public void setFechaEvento(Date fechaEvento) {
		this.fechaEvento = fechaEvento;
	}
	public String getObservacionDeGuardia() {
		return observacionDeGuardia;
	}
	public void setObservacionDeGuardia(String observacionDeGuardia) {
		this.observacionDeGuardia = observacionDeGuardia;
	}
	public Boolean getOcurrencia() {
		return ocurrencia;
	}
	public void setOcurrencia(Boolean ocurrencia) {
		this.ocurrencia = ocurrencia;
	}
	public Usuario getUsuarioSector() {
		return usuarioResponsableSector;
	}
	public void setUsuarioSector(Usuario usuarioResponsableSector) {
		this.usuarioResponsableSector = usuarioResponsableSector;
	}
	public Usuario getUsurioGuardia() {
		return usurioGuardia;
	}
	public void setUsurioGuardia(Usuario usurioGuardia) {
		this.usurioGuardia = usurioGuardia;
	}
	@Override
	public String toString() {
		return "Evento [idEvento=" + idEvento + ", descripcion=" + descripcion + ", fechaEvento=" + fechaEvento
				+ ", observacionDeGuardia=" + observacionDeGuardia + ", ocurrencia=" + ocurrencia
				+ ", usuarioResponsableSector=" + usuarioResponsableSector + ", usurioGuardia=" + usurioGuardia + "]";
	}
	
	
	
}
