package com.practicasupervisada.guardia2.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.practicasupervisada.guardia2.domain.RetiroMaterial;

public interface RetiroMaterialRepo extends JpaRepository<RetiroMaterial, Integer> {
	
	public List<RetiroMaterial> findAllByOrderByFechaLimiteAsc();
	
}
