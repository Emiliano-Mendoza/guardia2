package com.practicasupervisada.guardia2.service;

import java.util.List;
import java.util.Optional;

import com.practicasupervisada.guardia2.domain.SectorTrabajo;

public interface SectorTrabajoService {
	
	public SectorTrabajo crearSectorTrabajo(SectorTrabajo s);
	public List<SectorTrabajo> getAllSectorTrabajo();
	public void eliminarSectorTrabajo(int idSectorTrabajo);
	public Optional<SectorTrabajo> findById(int idSectorTrabajo);
	
}
