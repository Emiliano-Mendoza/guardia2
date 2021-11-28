package com.practicasupervisada.guardia2.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="RetiroMaterial")
public class RetiroMaterial {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idRetiro;
	
	private String descripcion;
	private Date fechaLimite;
	private Date fechaRetiro;
	
	private String observacionGuardia;
	
	@ManyToOne
	@JoinColumn(name = "ID_UsuarioGuardia")
	@JsonIgnore
	private Usuario usuarioGuardia;
	@ManyToOne
	@JoinColumn(name = "ID_UsuarioSector")
	@JsonIgnore
	private Usuario usuarioResponsableSector;
	@ManyToOne
	@JoinColumn(name = "ID_Personal")
	private Personal personal;
	
	@JsonIgnore
	public Usuario getUsuarioGuardia() {
		return usuarioGuardia;
	}
	public void setUsuarioGuardia(Usuario usuarioGuardia) {
		this.usuarioGuardia = usuarioGuardia;
	}
	@JsonIgnore
	public Usuario getUsuarioSector() {
		return usuarioResponsableSector;
	}
	public void setUsuarioSector(Usuario usuarioResponsableSector) {
		this.usuarioResponsableSector = usuarioResponsableSector;
	}
	public Personal getPersonal() {
		return personal;
	}
	public void setPersonal(Personal personal) {
		this.personal = personal;
	}
	public int getIdRetiro() {
		return idRetiro;
	}
	public void setIdRetiro(int idRetiro) {
		this.idRetiro = idRetiro;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getFechaLimite() {
		return fechaLimite;
	}
	public void setFechaLimite(Date fechaLimite) {
		this.fechaLimite = fechaLimite;
	}
	public Date getFechaRetiro() {
		return fechaRetiro;
	}
	public void setFechaRetiro(Date fechaRetiro) {
		this.fechaRetiro = fechaRetiro;
	}
	public String getObservacionGuardia() {
		return observacionGuardia;
	}
	public void setObservacionGuardia(String observacionGuardia) {
		this.observacionGuardia = observacionGuardia;
	}
	@Override
	public String toString() {
		return "RetiroMaterial [idRetiro=" + idRetiro + ", descripcion=" + descripcion + ", fechaLimite=" + fechaLimite
				+ ", fechaRetiro=" + fechaRetiro + ", observacionGuardia=" + observacionGuardia + ", personal="
				+ personal + "]";
	}

	
	
	
	
}
