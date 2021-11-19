package com.practicasupervisada.guardia2.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.practicasupervisada.guardia2.domain.Evento;
import com.practicasupervisada.guardia2.service.EventoService;

@Controller
@RequestMapping("/views/evento")
public class EventoController {
	
	@Autowired
	private EventoService eventoServ;
	
	@GetMapping
	public String obtenerListaEventos(Model model) {
		
		try {
			List<Evento> listaEventos = eventoServ.getAllEvento();
			
			model.addAttribute("listaEventos", listaEventos);
		}catch(Exception e) {
			
		}
		
		//falta implementar
		return null;
	}
	
	@GetMapping("/nuevo")
	public String nuevoEvento(Model model) {
		
		Evento evento = new Evento();
		model.addAttribute("evento", evento);
		
		//borrar
		List<Evento> listaEventos = eventoServ.getAllEvento();
		model.addAttribute("listaEventos", listaEventos);
		
		return "views/evento/aviso";
	}
	
	@PostMapping("/crear")
	public String crearNuevoEvento(@RequestParam(name = "desc") String desc,
											@RequestParam(name = "fechaEvento") String fechaEvento){
		
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		
		try {
			Date fechaAux = formatter.parse(fechaEvento);
			
			Evento evento = new Evento();
			evento.setDescripcion(desc);
			evento.setFechaEvento(fechaAux);
			
			eventoServ.crearEvento(evento);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return "redirect:/views/evento/nuevo";
	}
}
