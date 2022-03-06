package com.practicasupervisada.guardia2.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practicasupervisada.guardia2.dao.TransitoRepo;
import com.practicasupervisada.guardia2.domain.Transito;
import com.practicasupervisada.guardia2.logs.LogAspect;
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

	@Override
	public Transito actualizarTransito(Transito t) {
		
		return transitoRepo.save(t);
	}

	@Override
	public void inspecciondarTransitosExpirados() {
		
		List<Transito> listaTransito = transitoRepo.findAllByOrderByFechaSalidaTransitoriaAsc()
				.stream()
				.filter(t -> t.getFechaReingreso()==null && t.getUsuarioIngreso()==null)
				.collect(Collectors.toList());
		
		for(Transito transito :listaTransito) {
		      
		    //Date fechaAux = new Date(transito.getFechaSalidaTransitoria().getTime() + (1000 * 60 * 60 * 24));
			//10 min despues de que ingrese 
			Date fechaAux = new Date(transito.getFechaSalidaTransitoria().getTime() + (1000 * 60 * 1));
			Date fechaPresente = new Date();
			
			//pregunto si la fecha presente es superior a la fecha limite del transito
			if(fechaPresente.after(fechaAux)) {
				
				transito.setComentario2("Tránsito expirado");
			    transito.setFechaReingreso(fechaAux);
			    transito.setUsuarioIngreso(transito.getUsuarioEgreso());
			    transito.getAsistencia().setEnTransito(false);
			    
			    LogAspect.logger.info("@@@@@@ Tránsito expirado: " + transito);
			    
			    crearTransito(transito);
				
			}								    
		}
	}

}
