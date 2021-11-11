package com.practicasupervisada.guardia2.domain;



import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Personal")
public class Personal implements Comparable<Personal> {
	
	@Id
	private int nroLegajo;
	private String nombre;
	private String apellido;
	private String sector;
	
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public int getNroLegajo() {
		return nroLegajo;
	}
	public void setNroLegajo(int nroLegajo) {
		this.nroLegajo = nroLegajo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	@Override
	public String toString() {
		return "Personal [nroLegajo=" + nroLegajo + ", nombre=" + nombre + ", apellido=" + apellido + ", sector="
				+ sector + "]";
	}

	@Override
	public int compareTo(Personal o) {
		
		if(this.nroLegajo > o.nroLegajo) {
			return 1;
		}else if (this.nroLegajo < o.nroLegajo) {
			return -1;
		}else {
			 return 0;
		}

	}
	
	
}
