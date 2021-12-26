package com.practicasupervisada.guardia2.service;

import java.util.List;
import java.util.Optional;

import com.practicasupervisada.guardia2.domain.Transito;

public interface TransitoService {
	
	public Transito crearTransito(Transito t);
	public List<Transito> getAllAsistencias();
	public void eliminarTransito(int idTransito);
	public Optional<Transito> findById(int idTransito);
	public List<Transito> findAllByOrderByFechaSalidaTransitoriaAsc();
	
}
