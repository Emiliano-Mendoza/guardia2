package com.practicasupervisada.guardia2.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practicasupervisada.guardia2.domain.AsistenciaProveedor;

public interface AsistenciaProveedorRepo extends JpaRepository<AsistenciaProveedor, Integer> {
	
	public List<AsistenciaProveedor> findAllByOrderByEntradaAsc();
	
}
