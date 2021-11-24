package com.practicasupervisada.guardia2.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practicasupervisada.guardia2.dao.EventoRepo;
import com.practicasupervisada.guardia2.domain.Evento;
import com.practicasupervisada.guardia2.service.EventoService;

@Service
public class EventoServiceImp implements EventoService {
	
	@Autowired
	private EventoRepo eventoRepo;
	
	@Override
	public Evento crearEvento(Evento evento) {
		return eventoRepo.save(evento);
	}

	@Override
	public List<Evento> getAllEvento() {
		return eventoRepo.findAll();
	}

	@Override
	public void eliminarEvento(int idEvento) {
		eventoRepo.deleteById(idEvento);

	}

	@Override
	public Optional<Evento> findById(int idEvento) {
		return eventoRepo.findById(idEvento);
	}

	@Override
	public List<Evento> findAllByOrderByFechaEventoAsc() {
		return eventoRepo.findAllByOrderByFechaEventoAsc();
	}

}
