package com.practicasupervisada.guardia2.service;

import java.util.List;
import java.util.Optional;

import com.practicasupervisada.guardia2.domain.Vehiculo;

public interface VehiculoService {
	
	public Vehiculo crearVehiculo(Vehiculo p);
	public List<Vehiculo> getAllVehiculo();
	public void eliminarVehiculo(int idVehiculo);
	public Optional<Vehiculo> findById(int idVehiculo);
}
