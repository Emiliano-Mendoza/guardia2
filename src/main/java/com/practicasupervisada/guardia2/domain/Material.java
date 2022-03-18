package com.practicasupervisada.guardia2.domain;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="Material")
public class Material {
	
	@Id
	private int idMaterial;
	
	private String material;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "materiales")
	@JsonBackReference
	private Set<RetiroMaterial> retiro = new HashSet<>();
	
	public int getId_material() {
		return idMaterial;
	}

	public void setId_material(int id_material) {
		this.idMaterial = id_material;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	@Override
	public String toString() {
		return "Material [idMaterial=" + idMaterial + ", material=" + material + "]";
	}
	
	
}
