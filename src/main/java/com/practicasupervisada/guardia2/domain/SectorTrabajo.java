package com.practicasupervisada.guardia2.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SectorTrabajo")
public class SectorTrabajo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idSector;	
	private String sector;
	
	
	public int getIdSector() {
		return idSector;
	}

	public void setIdSector(int idSector) {
		this.idSector = idSector;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	@Override
	public String toString() {
		return "SectorTrabajo [idSector=" + idSector + ", sector=" + sector + "]";
	}
	
	
}
