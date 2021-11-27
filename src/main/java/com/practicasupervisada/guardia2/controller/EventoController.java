package com.practicasupervisada.guardia2.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
			List<Evento> listaEventos = eventoServ.findAllByOrderByFechaEventoAsc();
		    			
			Date today = new Date();
			Date beforeYesterday = new Date(today.getTime() - 2*(1000 * 60 * 60 * 24));	
			
			listaEventos = listaEventos.stream()
					.filter(e -> (e.getOcurrencia()==false && e.getFechaEvento().after(beforeYesterday)))
					.collect(Collectors.toList());				
			
			model.addAttribute("listaEventos", listaEventos);
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return "views/evento/listadoEventos";
	}
	
	@GetMapping("/nuevo")
	public String nuevoEvento(Model model) {
		
		try {
			Evento evento = new Evento();

			List<Evento> listaEventos = eventoServ.findAllByOrderByFechaEventoAsc();
			
			Date today = new Date();
			Date beforeYesterday = new Date(today.getTime() - 2*(1000 * 60 * 60 * 24));
			
			//Los eventos proximos a mostrar deben depender del usuario que los hizo	
			listaEventos = listaEventos.stream()
					.filter(e -> (e.getOcurrencia()==false && e.getFechaEvento().after(beforeYesterday)))
					.collect(Collectors.toList());
			
			model.addAttribute("evento", evento);
			model.addAttribute("listaEventos", listaEventos);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return "views/evento/aviso";
	}
	
	@PostMapping("/crear")
	public String crearNuevoEvento(@RequestParam(name = "desc") String desc,
								   @RequestParam(name = "fechaEvento") String fechaEvento,
								   RedirectAttributes atributos){
		
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		
		try {
			Date fechaAux = formatter.parse(fechaEvento);
			
			Evento evento = new Evento();
			evento.setDescripcion(desc);
			evento.setFechaEvento(fechaAux);
			evento.setOcurrencia(false);
			
			eventoServ.crearEvento(evento);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		atributos.addFlashAttribute("success", "Nuevo evento creado exitosamente!");
		return "redirect:/views/evento/nuevo";
	}
	
	@PostMapping("/ocurrencia/{idEvento}")
	public String egreso(@PathVariable("idEvento") int idEvento, 
			@RequestParam(name = "observacionGuardia") String observacionGuardia,
			RedirectAttributes atributos) {
				
		try {
			Evento evento = eventoServ.findById(idEvento).orElseThrow();
			
			evento.setOcurrencia(true);
			
			evento.setObservacionDeGuardia(observacionGuardia);
			
			eventoServ.crearEvento(evento);
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		atributos.addFlashAttribute("success", "Evento registrado exitosamente!");
		return "redirect:/views/evento";
	}
}
