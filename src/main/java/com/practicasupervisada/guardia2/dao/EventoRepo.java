package com.practicasupervisada.guardia2.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practicasupervisada.guardia2.domain.Evento;

public interface EventoRepo extends JpaRepository<Evento, Integer> {
	
	public List<Evento> findAllByOrderByFechaEventoAsc();
	
}
