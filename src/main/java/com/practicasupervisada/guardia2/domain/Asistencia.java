package com.practicasupervisada.guardia2.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="Asistencia")
public class Asistencia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idAsistencia;
	private Date entrada;
	private Date salida;
	private Boolean enTransito;
	
	@ManyToOne
	@JoinColumn(name = "ID_Usuario_Ingreso")
	
	private Usuario usuarioIngreso;
	
	@ManyToOne
	@JoinColumn(name = "ID_Usuario_Egreso")
	
	private Usuario usuarioEgreso;
	
	@ManyToOne
	@JoinColumn(name = "id_Personal")
	private Personal personal;
			
	
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
	public Personal getPersonal() {
		return personal;
	}
	public void setPersonal(Personal personal) {
		this.personal = personal;
	}
	
	public int getIdAsistencia() {
		return idAsistencia;
	}
	public void setIdAsistencia(int idAsistencia) {
		this.idAsistencia = idAsistencia;
	}
	public Date getEntrada() {
		return entrada;
	}
	public void setEntrada(Date entrada) {
		this.entrada = entrada;
	}
	public Date getSalida() {
		return salida;
	}
	public void setSalida(Date salida) {
		this.salida = salida;
	}
	public Boolean getEnTransito() {
		return enTransito;
	}
	public void setEnTransito(Boolean enTransito) {
		this.enTransito = enTransito;
	}
	@Override
	public String toString() {
		return "Asistencia [idAsistencia=" + idAsistencia + ", entrada=" + entrada + ", salida=" + salida
				+ ", enTransito=" + enTransito + ", usuarioIngreso=" + usuarioIngreso + ", usuarioEgreso="
				+ usuarioEgreso + ", personal=" + personal + "]";
	}

	
	
}
