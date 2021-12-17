package com.practicasupervisada.guardia2.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practicasupervisada.guardia2.domain.Proveedor;

public interface ProveedorRepo extends JpaRepository<Proveedor, Integer> {
	
	public boolean existsByRazonSocial(String razon);
	
}
