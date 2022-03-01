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
@Table(name="Transito")
public class Transito {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idTransito;
	private Date fechaSalidaTransitoria;
	private Date fechaReingreso;
	private String comentario;
	private String comentario2;
	
	@ManyToOne
	@JoinColumn(name = "id_Personal")
	private Personal personal;
	
	@ManyToOne
	@JoinColumn(name = "id_Vehiculo")
	private Vehiculo vehiculo;
	
	@ManyToOne
	@JoinColumn(name = "id_Vehiculo2")
	private Vehiculo vehiculo2;
	
	@ManyToOne
	@JoinColumn(name = "id_Asistencia")
	private Asistencia asistencia;
	
	@ManyToOne
	@JoinColumn(name = "ID_Usuario_Ingreso")
	private Usuario usuarioIngreso;
	
	@ManyToOne
	@JoinColumn(name = "ID_Usuario_Egreso")

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


	public Usuario getUsuarioIngreso() {
		return usuarioIngreso;
	}

	public void setUsuarioIngreso(Usuario usuarioIngreso) {
		this.usuarioIngreso = usuarioIngreso;
	}
	

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

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public Vehiculo getVehiculo2() {
		return vehiculo2;
	}

	public void setVehiculo2(Vehiculo vehiculo2) {
		this.vehiculo2 = vehiculo2;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getComentario2() {
		return comentario2;
	}

	public void setComentario2(String comentario2) {
		this.comentario2 = comentario2;
	}

	@Override
	public String toString() {
		return "Transito [idTransito=" + idTransito + ", fechaSalidaTransitoria=" + fechaSalidaTransitoria
				+ ", fechaReingreso=" + fechaReingreso + ", comentario=" + comentario + ", comentario2=" + comentario2
				+ ", personal=" + personal + ", vehiculo=" + vehiculo + ", vehiculo2=" + vehiculo2 + ", asistencia="
				+ asistencia + "]";
	}

	
}
