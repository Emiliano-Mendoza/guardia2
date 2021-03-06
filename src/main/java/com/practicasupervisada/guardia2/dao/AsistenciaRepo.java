package com.practicasupervisada.guardia2.dao;



import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practicasupervisada.guardia2.domain.Asistencia;

public interface AsistenciaRepo extends JpaRepository<Asistencia, Integer> {
	
	public List<Asistencia> findAllByOrderByEntradaAsc();
	
	
	public List<Asistencia> findAllByEntradaLessThanEqualAndEntradaGreaterThanEqualOrderByEntradaAsc(Date endDate, Date startDate);
}
