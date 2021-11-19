package com.practicasupervisada.guardia2.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="RetiroMaterial")
public class RetiroMaterial {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idRetiro;
	
	@Size(max = 1000)
	private String descripcion;
	
	private Date fechaLimite;
	private Date fechaRetiro;
	
	@Size(max = 1000)
	private String observacionGuardia;
	
	@ManyToOne
	@JoinColumn(name = "ID_UsuarioGuardia")
	private Usuario usuarioGuardia;
	@ManyToOne
	@JoinColumn(name = "ID_UsuarioSector")
	private Usuario usuarioResponsableSector;
	@ManyToOne
	@JoinColumn(name = "ID_Personal")
	private Personal personal;
	
	public Usuario getUsuarioGuardia() {
		return usuarioGuardia;
	}
	public void setUsuarioGuardia(Usuario usuarioGuardia) {
		this.usuarioGuardia = usuarioGuardia;
	}
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
				+ ", fechaRetiro=" + fechaRetiro + ", observacionGuardia=" + observacionGuardia + ", usuarioGuardia="
				+ usuarioGuardia + ", usuarioResponsableSector=" + usuarioResponsableSector + ", personal=" + personal
				+ "]";
	}
	
	
	
}
