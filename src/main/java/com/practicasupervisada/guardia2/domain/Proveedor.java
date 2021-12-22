package com.practicasupervisada.guardia2.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="Proveedor")
public class Proveedor{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idProveedor;
	@NotEmpty
	private String razonSocial;

		
	public int getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}

	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	@Override
	public String toString() {
		return "Proveedor [idProveedor=" + idProveedor + ", razonSocial=" + razonSocial + "]";
	}
	
	
}
