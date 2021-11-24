package com.practicasupervisada.guardia2.service;

import java.util.List;
import java.util.Optional;

import com.practicasupervisada.guardia2.domain.Evento;

public interface EventoService {
	
	public Evento crearEvento(Evento evento);
	public List<Evento> getAllEvento();
	public void eliminarEvento(int idEvento);
	public Optional<Evento> findById(int idEvento);
	public List<Evento> findAllByOrderByFechaEventoAsc();
	
}
