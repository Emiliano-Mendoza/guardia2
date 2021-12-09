package com.practicasupervisada.guardia2.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Transito")
public class Transito {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idTransito;
	private Date fechaSalidaTransitoria;
	private Date fechaReingreso;
	
	@ManyToOne
	@JoinColumn(name = "id_Personal")
	private Personal personal;
	
	@ManyToOne
	@JoinColumn(name = "id_Vehiculo")
	private Vehiculo vehiculo;
	
	@ManyToOne
	@JoinColumn(name = "id_Asistencia")
	@JsonBackReference
	private Asistencia asistencia;
	
	@ManyToOne
	@JoinColumn(name = "ID_Usuario_Ingreso")
	@JsonIgnore
	private Usuario usuarioIngreso;
	
	@ManyToOne
	@JoinColumn(name = "ID_Usuario_Egreso")
	@JsonIgnore
	private Usuario usuarioEgreso;

	public int getIdTransito() {
		return idTransito;
	}

	public void setIdTransito(int idTransito) {
		this.idTransito = idTransito;
	}

	public Date getFechaSalidaTransitoria() {
		return fechaSalidaTransitoria;
	}

	public void setFechaSalidaTransitoria(Date fechaSalidaTransitoria) {
		this.fechaSalidaTransitoria = fechaSalidaTransitoria;
	}

	public Date getFechaReingreso() {
		return fechaReingreso;
	}

	public void setFechaReingreso(Date fechaReingreso) {
		this.fechaReingreso = fechaReingreso;
	}

	public Personal getPersonal() {
		return personal;
	}

	public void setPersonal(Personal personal) {
		this.personal = personal;
	}

	@Override
	public String toString() {
		return "Transito [idTransito=" + idTransito + ", fechaSalidaTransitoria=" + fechaSalidaTransitoria
				+ ", fechaReingreso=" + fechaReingreso + ", personal=" + personal + "]";
	}
	
	@JsonIgnore
	public Usuario getUsuarioIngreso() {
		return usuarioIngreso;
	}

	public void setUsuarioIngreso(Usuario usuarioIngreso) {
		this.usuarioIngreso = usuarioIngreso;
	}
	
	@JsonIgnore
	public Usuario getUsuarioEgreso() {
		return usuarioEgreso;
	}

	public void setUsuarioEgreso(Usuario usuarioEgreso) {
		this.usuarioEgreso = usuarioEgreso;
	}

	public Asistencia getAsistencia() {
		return asistencia;
	}

	public void setAsistencia(Asistencia asistencia) {
		this.asistencia = asistencia;
	}
	
	
	
}
