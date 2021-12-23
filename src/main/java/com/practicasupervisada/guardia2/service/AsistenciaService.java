package com.practicasupervisada.guardia2.service;

import java.util.List;
import java.util.Optional;

import com.practicasupervisada.guardia2.domain.Asistencia;

public interface AsistenciaService {
	
	public Asistencia crearAsistencia(Asistencia asis);
	public List<Asistencia> getAllAsistencias();
	public void eliminarAsistencia(int idAsistencia);
	public Optional<Asistencia> findById(int idAsistencia);
	public List<Asistencia> findAllByOrderByEntradaAsc();
}
