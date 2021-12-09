package com.practicasupervisada.guardia2.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Vehiculo")
public class Vehiculo{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idVehiculo;
	private String patente;
	private String marca;
	private String modelo;
	
	
	public String getPatente() {
		return patente;
	}
	public void setPatente(String patente) {
		this.patente = patente;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(int idVehiculo) {
		this.idVehiculo = idVehiculo;
	}
	@Override
	public String toString() {
		return "Vehiculo [idVehiculo=" + idVehiculo + ", patente=" + patente + ", marca=" + marca + ", modelo=" + modelo
				+ "]";
	}
	
	
}
