package com.practicasupervisada.guardia2.domain;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	
	
	@ManyToOne
	@JoinColumn(name = "id_SectorTrabajo")
    private SectorTrabajo sectorTrabajo;
	
	private String imagen;
	
	private Boolean enabled;
	
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
		return "Personal [nroLegajo=" + nroLegajo + ", nombre=" + nombre + ", apellido=" + apellido + ", sectorTrabajo=" + sectorTrabajo + "]";
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
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public SectorTrabajo getSectorTrabajo() {
		return sectorTrabajo;
	}
	public void setSectorTrabajo(SectorTrabajo sectorTrabajo) {
		this.sectorTrabajo = sectorTrabajo;
	}
	
	
}
