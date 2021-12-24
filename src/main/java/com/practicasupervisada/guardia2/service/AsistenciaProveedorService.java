package com.practicasupervisada.guardia2.service;

import java.util.List;
import java.util.Optional;

import com.practicasupervisada.guardia2.domain.AsistenciaProveedor;

public interface AsistenciaProveedorService {
	
	public AsistenciaProveedor crearAsistencia(AsistenciaProveedor asis);
	public List<AsistenciaProveedor> getAllAsistencias();
	public void eliminarAsistencia(int idAsistencia);
	public Optional<AsistenciaProveedor> findById(int idAsistencia);
	public List<AsistenciaProveedor> findAllByOrderByEntradaAsc();
}
