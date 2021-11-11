package com.practicasupervisada.guardia2.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practicasupervisada.guardia2.dao.PersonalRepo;
import com.practicasupervisada.guardia2.domain.Personal;
import com.practicasupervisada.guardia2.service.PersonalService;

@Service
public class PersonalServiceImp implements PersonalService {
	
	@Autowired
	private PersonalRepo personalRepo;
	
	@Override
	public Personal crearPersonal(Personal p) {
		return personalRepo.save(p);
	}

	@Override
	public List<Personal> getAllPersonal() {
		return (List<Personal>) personalRepo.findAll();
	}

	@Override
	public void eliminarPersonal(int idPersonal) {
		personalRepo.deleteById(idPersonal);	
	}

	@Override
	public Optional<Personal> findById(int idPersonal) {
		return personalRepo.findById(idPersonal);
	}

}
