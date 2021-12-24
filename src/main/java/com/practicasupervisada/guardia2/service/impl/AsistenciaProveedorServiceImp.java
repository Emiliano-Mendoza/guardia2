package com.practicasupervisada.guardia2.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practicasupervisada.guardia2.dao.AsistenciaProveedorRepo;
import com.practicasupervisada.guardia2.domain.AsistenciaProveedor;
import com.practicasupervisada.guardia2.service.AsistenciaProveedorService;

@Service
public class AsistenciaProveedorServiceImp implements AsistenciaProveedorService {
	
	@Autowired
	private AsistenciaProveedorRepo asisRepo;
	
	@Override
	public AsistenciaProveedor crearAsistencia(AsistenciaProveedor asis) {
		return asisRepo.save(asis);
	}

	@Override
	public List<AsistenciaProveedor> getAllAsistencias() {
	
		return asisRepo.findAll();
	}

	@Override
	public void eliminarAsistencia(int idAsistencia) {
		asisRepo.deleteById(idAsistencia);

	}

	@Override
	public Optional<AsistenciaProveedor> findById(int idAsistencia) {
		
		return asisRepo.findById(idAsistencia);
	}

	@Override
	public List<AsistenciaProveedor> findAllByOrderByEntradaAsc() {
		return asisRepo.findAllByOrderByEntradaAsc();
	}

}
