package com.practicasupervisada.guardia2.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practicasupervisada.guardia2.dao.AcontecimientoRepo;
import com.practicasupervisada.guardia2.domain.Acontecimiento;
import com.practicasupervisada.guardia2.service.AcontecimientoService;

@Service
public class AcontecimientoServiceImp implements AcontecimientoService {

	@Autowired
	private AcontecimientoRepo aconRepo;
	
	@Override
	public Acontecimiento crearAcontecimiento(Acontecimiento acontecimiento) {
		return aconRepo.save(acontecimiento);
	}

	@Override
	public List<Acontecimiento> getAllAcontecimientos() {
		return aconRepo.findAll();
	}

	@Override
	public void eliminarAcontecimiento(int idAcontecimiento) {
		aconRepo.deleteById(idAcontecimiento);

	}

	@Override
	public Optional<Acontecimiento> findById(int idAcontecimiento) {
		return aconRepo.findById(idAcontecimiento);
	}

}
