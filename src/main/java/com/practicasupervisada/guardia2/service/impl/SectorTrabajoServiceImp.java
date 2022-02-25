package com.practicasupervisada.guardia2.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practicasupervisada.guardia2.dao.SectorTrabajoRepo;
import com.practicasupervisada.guardia2.domain.SectorTrabajo;
import com.practicasupervisada.guardia2.service.SectorTrabajoService;

@Service
public class SectorTrabajoServiceImp implements SectorTrabajoService {
	
	@Autowired
	private SectorTrabajoRepo sectorRepo;
	
	@Override
	public SectorTrabajo crearSectorTrabajo(SectorTrabajo s) {
		
		return sectorRepo.save(s);
	}

	@Override
	public List<SectorTrabajo> getAllSectorTrabajo() {
		
		return sectorRepo.findAll();
	}

	@Override
	public void eliminarSectorTrabajo(int idSectorTrabajo) {
		sectorRepo.deleteById(idSectorTrabajo);

	}

	@Override
	public Optional<SectorTrabajo> findById(int idSectorTrabajo) {
		
		return sectorRepo.findById(idSectorTrabajo);
	}

}
