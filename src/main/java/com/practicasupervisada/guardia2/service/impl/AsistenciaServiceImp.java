package com.practicasupervisada.guardia2.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.practicasupervisada.guardia2.dao.AsistenciaRepo;
import com.practicasupervisada.guardia2.domain.Asistencia;
import com.practicasupervisada.guardia2.service.AsistenciaService;

@Service
public class AsistenciaServiceImp implements AsistenciaService {
	
	@Autowired
	private AsistenciaRepo asistenciaRepo;
	
	@Override
	public Asistencia crearAsistencia(Asistencia asis) {		
		return asistenciaRepo.save(asis);
	}

	@Override
	public List<Asistencia> getAllAsistencias() {
		return asistenciaRepo.findAll();
	}

	@Override
	public void eliminarAsistencia(int idAsistencia) {
		asistenciaRepo.deleteById(idAsistencia);

	}

	@Override
	public Optional<Asistencia> findById(int idAsistencia) {
		return asistenciaRepo.findById(idAsistencia);
	}

	@Override
	public List<Asistencia> findAllByOrderByEntradaAsc() {
		return asistenciaRepo.findAllByOrderByEntradaAsc();
	}

	@Override
	public List<Asistencia> findAllByEntradaLessThanEqualAndEntradaGreaterThanEqual(Date endDate, Date startDate) {
		
		return asistenciaRepo.findAllByEntradaLessThanEqualAndEntradaGreaterThanEqualOrderByEntradaAsc(endDate, startDate);
	}

}
