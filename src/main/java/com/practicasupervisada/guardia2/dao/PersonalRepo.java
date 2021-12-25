package com.practicasupervisada.guardia2.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practicasupervisada.guardia2.domain.Personal;

public interface PersonalRepo extends JpaRepository<Personal, Integer> {
	
	public List<Personal> findAllByOrderByApellidoAsc();
	
}
