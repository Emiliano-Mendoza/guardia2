package com.practicasupervisada.guardia2.dao;



import org.springframework.data.jpa.repository.JpaRepository;

import com.practicasupervisada.guardia2.domain.Asistencia;


public interface AsistenciaRepo extends JpaRepository<Asistencia, Integer> {
	
}
