package com.practicasupervisada.guardia2.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practicasupervisada.guardia2.dao.ProveedorRepo;
import com.practicasupervisada.guardia2.domain.Proveedor;
import com.practicasupervisada.guardia2.service.ProveedorService;

@Service
public class ProovedorServiceImp implements ProveedorService {
	
	@Autowired
	private ProveedorRepo proveedorRepo;
	
	@Override
	public Proveedor crearProveedor(Proveedor p) {
		return proveedorRepo.save(p);
	}

	@Override
	public List<Proveedor> getAllProveedor() {
		return proveedorRepo.findAll();
	}

	@Override
	public void eliminarProveedor(int idProveedor) {
		proveedorRepo.deleteById(idProveedor);

	}

	@Override
	public Optional<Proveedor> findById(int idProveedor) {
		return proveedorRepo.findById(idProveedor);
	}

}
