package com.practicasupervisada.guardia2.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

	@Override
	public Boolean existeEvento(int idEvento) {
		return eventoRepo.existsById(idEvento);
	}

	@Override
	public List<Evento> buscarEventosProximosRangoFechas(Date date1, Date date2) {
	
		Date auxDate1 = new Date(date1.getTime() - 1);
		
		List<Evento> listaEventos = eventoRepo.findAllByOrderByFechaEventoAsc().stream()
				.filter(e ->  e.getFechaEvento() != null
			    && e.getDescripcion() != null
				&& (e.getFechaEvento().after(auxDate1))
				&& e.getFechaEvento().before(date2))
		.collect(Collectors.toList());
		
		return listaEventos;
	}

	@Override
	public Boolean validarOcurrenciaEvento(Evento evento) {
		
		Boolean validacion = false;
		
		if(eventoRepo.existsById(evento.getIdEvento())) {
			
			List<Evento> listaEventos = eventoRepo.findAllByOrderByFechaEventoAsc();
			
			Date today = new Date();
			Date beforeYesterday = new Date(today.getTime() - 2*(1000 * 60 * 60 * 24));	
			
			listaEventos = listaEventos.stream()
					.filter(e -> (e.getOcurrencia()==false 
								&& e.getCancelado()==false 
								&& e.getFechaEvento() != null
								&& e.getFechaEvento().after(beforeYesterday)
								&& e.getDescripcion() != null))
					.collect(Collectors.toList());
			
			
			// Se valida si el evento enviado está dentro de la lista de evento proximos			
			validacion = listaEventos.stream().anyMatch(e -> (e.getIdEvento()==evento.getIdEvento()));
			
		}
		
				
		return validacion;
	}

}
