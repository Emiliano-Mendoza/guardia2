package com.practicasupervisada.guardia2.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="Personal")
public class Personal implements Comparable<Personal> {
	
	@Id
	@Min(1)
	@Max(5000)
	private int nroLegajo;
	@NotEmpty
	private String nombre;
	@NotEmpty
	private String apellido;
	@NotEmpty
	private String sector;
	@Lob
	private String image;
	
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public Integer getNroLegajo() {
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
