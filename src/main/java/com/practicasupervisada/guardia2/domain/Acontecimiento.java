package com.practicasupervisada.guardia2.domain;



import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Acontecimiento")
public class Acontecimiento {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idAcontencimiento;
	private Date fecha;
	@NotEmpty
	@Pattern(regexp = "[0-9]{2}:[0-9]{2}")
	private String ronda;
	@NotEmpty
	private String descripcion;
	
	@ManyToOne
	@JoinColumn(name = "ID_UsuarioGuardia")
	@JsonIgnore
	private Usuario usuario;
	
	public int getIdAcontencimiento() {
		return idAcontencimiento;
	}
	public void setIdAcontencimiento(int idAcontencimiento) {
		this.idAcontencimiento = idAcontencimiento;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getRonda() {
		return ronda;
	}
	public void setRonda(String ronda) {
		this.ronda = ronda;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	@JsonIgnore
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	@Override
	public String toString() {
		return "Acontecimiento [idAcontencimiento=" + idAcontencimiento + ", fecha=" + fecha + ", ronda=" + ronda
				+ ", descripcion=" + descripcion + ", usuario=" + usuario + "]";
	}
}
