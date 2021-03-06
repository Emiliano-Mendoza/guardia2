package com.practicasupervisada.guardia2.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.practicasupervisada.guardia2.service.UsuarioService;

@Controller
@RequestMapping("/views/evento")
public class EventoController {
	
	@Autowired
	private EventoService eventoServ;
	@Autowired
	private UsuarioService usuarioServ;
	
	@GetMapping
	public String obtenerListaNotificacionesEventos(Model model) {
		
		try {
			List<Evento> listaEventos = eventoServ.findAllByOrderByFechaEventoAsc();
		    			
			Date today = new Date();
			Date beforeYesterday = new Date(today.getTime() - 2*(1000 * 60 * 60 * 24));	
			
			listaEventos = listaEventos.stream()
					.filter(e -> (e.getOcurrencia()==false 
								&& e.getCancelado()==false 
								&& e.getFechaEvento() != null
								&& e.getFechaEvento().after(beforeYesterday)
								&& e.getDescripcion() != null))
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
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			
			//Los eventos proximos a mostrar deben depender del usuario que los hizo
			listaEventos = listaEventos.stream()
					.filter(e -> (e.getOcurrencia()==false
							&& e.getFechaEvento() != null
							&& e.getDescripcion() != null
							&& e.getUsuarioSector() != null
					        && e.getFechaEvento().after(beforeYesterday)
							&& e.getUsuarioSector().getUsuario().equals(auth.getName())))
					.collect(Collectors.toList());
			
			//ordeno la lista para dejar a los eventos cancelados al final
			List<Evento> listaEventosCancelados = listaEventos.stream()
					.filter(e -> (e.getCancelado()==true))
					.collect(Collectors.toList());
			listaEventos = listaEventos.stream()
					.filter(e -> (e.getCancelado()==false))
					.collect(Collectors.toList());
			
			List<Evento> listaOrdenada = new ArrayList<>();
			listaOrdenada.addAll(listaEventos);
			listaOrdenada.addAll(listaEventosCancelados);
			
			model.addAttribute("evento", evento);
			model.addAttribute("listaEventos", listaOrdenada);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return "views/evento/aviso";
	}
	
	@PostMapping("/crear")
	public String crearNuevaNotificacionDeEvento(@RequestParam(name = "desc") String desc,
								   @RequestParam(name = "fechaEvento") String fechaEvento,
								   RedirectAttributes atributos){
		
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
				
		try {
			Date fechaAux = formatter.parse(fechaEvento);
			
			Evento evento = new Evento();
			evento.setDescripcion(desc);
			evento.setFechaEvento(fechaAux);
			evento.setOcurrencia(false);
			evento.setCancelado(false);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			evento.setUsuarioSector(usuarioServ.findByUsuario(auth.getName()));
			
			eventoServ.crearEvento(evento);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		atributos.addFlashAttribute("success", "Nuevo evento creado exitosamente!");
		return "redirect:/views/evento/nuevo";
	}
	
	@PostMapping("/ocurrencia/{idEvento}")
	public String registrarOcurrenciaDeEvento(@PathVariable("idEvento") int idEvento, 
						 @RequestParam(name = "observacionGuardia") String observacionGuardia,
						 RedirectAttributes atributos) {
				
		try {
			Evento evento = eventoServ.findById(idEvento).orElseThrow();
			
			if(eventoServ.validarOcurrenciaEvento(evento)) {
				evento.setOcurrencia(true);
				evento.setObservacionDeGuardia(observacionGuardia);
				
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				evento.setUsurioGuardia(usuarioServ.findByUsuario(auth.getName()));
				
				eventoServ.crearEvento(evento);
			}
			else {
				new Exception("Evento Invalido");
			}
			
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		atributos.addFlashAttribute("success", "Evento registrado exitosamente!");
		return "redirect:/views/evento";
	}
	
	@PostMapping("/editar/{idEvento}")
	public String editarNotificacionDeEvento(@PathVariable("idEvento") int idEvento,
							   @RequestParam(name = "descripcion") String descripcion,
							   @RequestParam(name = "fechaEvento") String fechaEvento,
							   RedirectAttributes atributos) {
		
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		
		try {
			
			if(!eventoServ.existeEvento(idEvento)) {				
				atributos.addFlashAttribute("error", "Evento inexistente");
				return "redirect:/views/evento/nuevo";
			}
			
			Evento evento = eventoServ.findById(idEvento).orElseThrow();
			evento.setDescripcion(descripcion);
			
			Date fechaAux = formatter.parse(fechaEvento);			
			evento.setFechaEvento(fechaAux);
			
			eventoServ.crearEvento(evento);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		atributos.addFlashAttribute("success", "Evento editado exitosamente!");
		return "redirect:/views/evento/nuevo";
	}
	
	@PostMapping("/cancelar/{idEvento}")
	public String cancelarNotificacion(@PathVariable("idEvento") int idEvento,
							   @RequestParam(name = "descripcionCancelacion") String descripcionCancelacion,
							   RedirectAttributes atributos) {		
		
		try {
			
			if(!eventoServ.existeEvento(idEvento)) {				
				atributos.addFlashAttribute("error", "Evento inexistente");
				return "redirect:/views/evento/nuevo";
			}
			
			Evento evento = eventoServ.findById(idEvento).orElseThrow();
			
			evento.setDescripcionCancelacion(descripcionCancelacion);
			evento.setCancelado(true);
			evento.setOcurrencia(false);
			
			eventoServ.crearEvento(evento);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		atributos.addFlashAttribute("success", "Evento cancelado exitosamente!");
		return "redirect:/views/evento/nuevo";
	}
	
	@GetMapping("/previos")
	public String listarEventosAnteriores(Model model,
							@RequestParam(name = "date_range") String date_range){
			
		String[] parts = date_range.split("-");
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaInicioAux;
		Date fechaFinalAux;
		
		try {
			fechaInicioAux = formatter.parse(parts[0]);
			fechaFinalAux = new Date(formatter.parse(parts[1]).getTime() + (1000 * 60 * 60 * 24));
			
			List<Evento> listaEventos = eventoServ.buscarEventosProximosRangoFechas(fechaInicioAux, fechaFinalAux);
			
			model.addAttribute("listaEventos", listaEventos);
			model.addAttribute("diaInicial", fechaInicioAux);
			model.addAttribute("diaFinal", fechaFinalAux);
		} catch (ParseException e) {
			
			//si el formato de fecha no es correcto se muestra este error
			e.printStackTrace();
		}
					
		
		return "/views/evento/verEventosAnteriores";
	}
	
	@GetMapping("/eventos-usuario")
	public String listarEventosAnterioresUsuario(Model model){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		List<Evento> listaEventos = eventoServ.findAllByOrderByFechaEventoAsc()
				.stream()
				.filter(e -> e.getUsuarioSector().getUsuario().equals(auth.getName())
						     && e.getFechaEvento() != null
						     && e.getDescripcion() != null)
				.collect(Collectors.toList());
		
		model.addAttribute("listaEventos", listaEventos);
	
		return "/views/evento/verEventosAnteriores";
	}
	
}
