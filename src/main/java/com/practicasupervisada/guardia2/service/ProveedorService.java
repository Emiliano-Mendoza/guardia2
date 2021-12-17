package com.practicasupervisada.guardia2.service;

import java.util.List;
import java.util.Optional;

import com.practicasupervisada.guardia2.domain.Proveedor;

public interface ProveedorService {
	
	public Proveedor crearProveedor(Proveedor p);
	public List<Proveedor> getAllProveedor();
	public void eliminarProveedor(int idProveedor);
	public Optional<Proveedor> findById(int idProveedor);
	public boolean existsByRazonSocial(String razon);
	
}
