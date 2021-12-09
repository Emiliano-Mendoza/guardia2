package com.practicasupervisada.guardia2.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practicasupervisada.guardia2.dao.VehiculoRepo;
import com.practicasupervisada.guardia2.domain.Vehiculo;
import com.practicasupervisada.guardia2.service.VehiculoService;

@Service
public class VehiculoServiceImpl implements VehiculoService {
	
	@Autowired
	private VehiculoRepo vehiculoRepo;
	
	@Override
	public Vehiculo crearVehiculo(Vehiculo p) {
		
		return vehiculoRepo.save(p);
	}

	@Override
	public List<Vehiculo> getAllVehiculo() {

		return vehiculoRepo.findAll();
	}

	@Override
	public void eliminarVehiculo(int idVehiculo) {
		vehiculoRepo.deleteById(idVehiculo);

	}

	@Override
	public Optional<Vehiculo> findById(int idVehiculo) {
	
		return vehiculoRepo.findById(idVehiculo);
	}

}
