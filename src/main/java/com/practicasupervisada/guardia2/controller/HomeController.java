package com.practicasupervisada.guardia2.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.practicasupervisada.guardia2.domain.Evento;
import com.practicasupervisada.guardia2.domain.RetiroMaterial;
import com.practicasupervisada.guardia2.service.EventoService;
import com.practicasupervisada.guardia2.service.RetiroMaterialService;

@Controller
public class HomeController {
	
	@Autowired
	private RetiroMaterialService retiroServ;
	@Autowired
	private EventoService eventoServ;
	
	@GetMapping({"/home", "/index","/"})
	public String index(Model model) {
		
		List<RetiroMaterial> listaRetiros = retiroServ.findAllByOrderByFechaLimiteAsc();
		
		//traigo solo aquellos retiros que no hayas sucedido y que la autorizacion aun este vigente
		listaRetiros = listaRetiros.stream()
				.filter(ret -> (ret.getFechaRetiro()==null && ret.getFechaLimite().after(new Date())))
				.collect(Collectors.toList());
		
		model.addAttribute("listaRetiros", listaRetiros);
				
		//
		
		List<Evento> listaEventos = eventoServ.findAllByOrderByFechaEventoAsc();
		
		Date today = new Date();
		Date beforeYesterday = new Date(today.getTime() - 2*(1000 * 60 * 60 * 24));	
		
		listaEventos = listaEventos.stream()
				.filter(e -> (e.getOcurrencia()==false && e.getCancelado()==false && e.getFechaEvento().after(beforeYesterday)))
				.collect(Collectors.toList());				
							
		model.addAttribute("listaEventos", listaEventos);
				
		return "home";
	}
	
}
