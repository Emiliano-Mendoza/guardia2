package com.practicasupervisada.guardia2.service;

import java.util.List;
import java.util.Optional;

import com.practicasupervisada.guardia2.domain.Acontecimiento;

public interface AcontecimientoService {
	
	public Acontecimiento crearAcontecimiento(Acontecimiento acontecimiento);
	public List<Acontecimiento> getAllAcontecimientos();
	public void eliminarAcontecimiento(int idAcontecimiento);
	public Optional<Acontecimiento> findById(int idAcontecimiento);
	
}
