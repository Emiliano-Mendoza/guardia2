package com.practicasupervisada.guardia2.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practicasupervisada.guardia2.dao.TransitoRepo;
import com.practicasupervisada.guardia2.domain.Transito;
import com.practicasupervisada.guardia2.service.TransitoService;

@Service
public class TransitoServiceImp implements TransitoService {

	@Autowired
	private TransitoRepo transitoRepo;
	
	@Override
	public Transito crearTransito(Transito t) {
		return transitoRepo.save(t);
	}

	@Override
	public List<Transito> getAllAsistencias() {
		return transitoRepo.findAll();
	}

	@Override
	public void eliminarTransito(int idTransito) {
		transitoRepo.deleteById(idTransito);

	}

	@Override
	public Optional<Transito> findById(int idTransito) {
		return transitoRepo.findById(idTransito);
	}

	@Override
	public List<Transito> findAllByOrderByFechaSalidaTransitoriaAsc() {
		// TODO Auto-generated method stub
		return transitoRepo.findAllByOrderByFechaSalidaTransitoriaAsc();
	}

}
