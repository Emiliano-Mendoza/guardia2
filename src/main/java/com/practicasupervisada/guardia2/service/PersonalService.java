package com.practicasupervisada.guardia2.service;

import java.util.List;
import java.util.Optional;

import com.practicasupervisada.guardia2.domain.Personal;

public interface PersonalService {
	
	public Personal crearPersonal(Personal p);
	public List<Personal> getAllPersonal();
	public void eliminarPersonal(int idPersonal);
	public Optional<Personal> findById(int idPersonal);
	public List<Personal> findAllByOrderByApellidoAsc();
}
