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
@Table(name="ingreso_proveedor")
public class AsistenciaProveedor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idAsistencia;
	private Date entrada;
	private Date salida;
	private String nombreChofer;
	private String patenteVehiculo;
	
	@ManyToOne
	@JoinColumn(name = "ID_Usuario_Ingreso")
	@JsonIgnore
	private Usuario usuarioIngreso;
	
	@ManyToOne
	@JoinColumn(name = "ID_Usuario_Egreso")
	@JsonIgnore
	private Usuario usuarioEgreso;
	
	@ManyToOne
	@JoinColumn(name = "id_Proveedor")
	private Proveedor proveedor;

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

	public String getNombreChofer() {
		return nombreChofer;
	}

	public void setNombreChofer(String nombreChofer) {
		this.nombreChofer = nombreChofer;
	}

	public String getPatenteVehiculo() {
		return patenteVehiculo;
	}

	public void setPatenteVehiculo(String patenteVehiculo) {
		this.patenteVehiculo = patenteVehiculo;
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

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	@Override
	public String toString() {
		return "AsistenciaProveedor [idAsistencia=" + idAsistencia + ", entrada=" + entrada + ", salida=" + salida
				+ ", nombreChofer=" + nombreChofer + ", patenteVehiculo=" + patenteVehiculo + ", usuarioIngreso="
				+ usuarioIngreso + ", usuarioEgreso=" + usuarioEgreso + ", proveedor=" + proveedor + "]";
	}
	
	
}
