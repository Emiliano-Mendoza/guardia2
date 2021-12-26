package com.practicasupervisada.guardia2.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practicasupervisada.guardia2.domain.Transito;

public interface TransitoRepo extends JpaRepository<Transito, Integer> {
	
	public List<Transito> findAllByOrderByFechaSalidaTransitoriaAsc();
	
}
