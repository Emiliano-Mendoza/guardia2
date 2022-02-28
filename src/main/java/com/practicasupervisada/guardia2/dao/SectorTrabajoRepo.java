package com.practicasupervisada.guardia2.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practicasupervisada.guardia2.domain.SectorTrabajo;

public interface SectorTrabajoRepo extends JpaRepository<SectorTrabajo, Integer> {
	
	public List<SectorTrabajo> findAllByOrderByIdSector();
	
}
