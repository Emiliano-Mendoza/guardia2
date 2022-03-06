package com.practicasupervisada.guardia2.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;


import com.practicasupervisada.guardia2.domain.Evento;
import com.practicasupervisada.guardia2.domain.RetiroMaterial;
import com.practicasupervisada.guardia2.domain.Transito;
import com.practicasupervisada.guardia2.service.EventoService;
import com.practicasupervisada.guardia2.service.RetiroMaterialService;
import com.practicasupervisada.guardia2.service.TransitoService;

@Controller
public class HomeController {
	
	@Autowired
	private RetiroMaterialService retiroServ;
	@Autowired
	private EventoService eventoServ;
	@Autowired
	private TransitoService transitoServ;
	
	@GetMapping({"/home", "/index","/"})
	public String index(Model model, @CookieValue(value = "planta", defaultValue = "atte") String planta) {
		
		List<RetiroMaterial> listaRetiros = retiroServ.findAllByOrderByFechaLimiteAsc();
		
		//traigo solo aquellos retiros que no hayas sucedido y que la autorizacion aun este vigente
		listaRetiros = listaRetiros.stream()
				.filter(ret ->  (ret.getFechaRetiro()==null
						&& ret.getFechaLimite() != null
						&& ret.getDescripcion() != null
						&& ret.getUsuarioSector() != null
						&& ret.getMateriales() != null
						&& ret.getFechaLimite().after(new Date())))
				.collect(Collectors.toList());
		
		model.addAttribute("listaRetiros", listaRetiros);
				
		//
		
		List<Evento> listaEventos = eventoServ.findAllByOrderByFechaEventoAsc();
		
		Date today = new Date();
		Date beforeYesterday = new Date(today.getTime() - 2*(1000 * 60 * 60 * 24));	
		
		listaEventos = listaEventos.stream()
				.filter(e -> (e.getOcurrencia()==false 
						&& e.getCancelado()==false   
						&& e.getFechaEvento() != null
						&& e.getDescripcion() != null
						&& e.getFechaEvento().after(beforeYesterday)))
				.collect(Collectors.toList());				
							
		model.addAttribute("listaEventos", listaEventos);
		//model.addAttribute("plantaCookie", planta);
		//System.out.println(planta);
		
		return "home";
	}
	
	//sacar
	@GetMapping("/home_ex")
	public String expirarTransitosAux(Model model) {
						
		expirarTransitos();
		
		//System.out.println("se ejecuto");
		
		List<RetiroMaterial> listaRetiros = retiroServ.findAllByOrderByFechaLimiteAsc();
		
		//traigo solo aquellos retiros que no hayas sucedido y que la autorizacion aun este vigente
		listaRetiros = listaRetiros.stream()
				.filter(ret ->  (ret.getFechaRetiro()==null
						&& ret.getFechaLimite() != null
						&& ret.getDescripcion() != null
						&& ret.getUsuarioSector() != null
						&& ret.getMateriales() != null
						&& ret.getFechaLimite().after(new Date())))
				.collect(Collectors.toList());
		
		model.addAttribute("listaRetiros", listaRetiros);
				
		//
		
		List<Evento> listaEventos = eventoServ.findAllByOrderByFechaEventoAsc();
		
		Date today = new Date();
		Date beforeYesterday = new Date(today.getTime() - 2*(1000 * 60 * 60 * 24));	
		
		listaEventos = listaEventos.stream()
				.filter(e -> (e.getOcurrencia()==false 
						&& e.getCancelado()==false   
						&& e.getFechaEvento() != null
						&& e.getDescripcion() != null
						&& e.getFechaEvento().after(beforeYesterday)))
				.collect(Collectors.toList());				
							
		model.addAttribute("listaEventos", listaEventos);
		
		
		
		return "home";
	}
	
	public void expirarTransitos() {
		
		List<Transito> listaTransito = transitoServ.findAllByOrderByFechaSalidaTransitoriaAsc()
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
			    transito.setFechaReingreso(new Date());
			    transito.setUsuarioIngreso(transito.getUsuarioEgreso());
			    transito.getAsistencia().setEnTransito(false);
			      
			    transitoServ.crearTransito(transito);
				
			}								    
		}
		
	}
	
	
}
