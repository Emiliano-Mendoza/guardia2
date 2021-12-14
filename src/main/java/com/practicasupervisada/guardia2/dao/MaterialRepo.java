package com.practicasupervisada.guardia2.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practicasupervisada.guardia2.domain.Material;

public interface MaterialRepo extends JpaRepository<Material, Integer> {

	public Material findByMaterial(String material);
	
}
