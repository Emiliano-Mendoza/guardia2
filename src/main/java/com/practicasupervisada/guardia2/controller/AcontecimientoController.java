package com.practicasupervisada.guardia2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.practicasupervisada.guardia2.domain.Acontecimiento;
import com.practicasupervisada.guardia2.service.AcontecimientoService;
import com.practicasupervisada.guardia2.service.UsuarioService;

@Controller
@RequestMapping("/views/acontecimiento")
public class AcontecimientoController {
	
	@Autowired
	private AcontecimientoService acontecimientoServ;
	@Autowired
	private UsuarioService usuarioServ;
	
	@GetMapping
	public String listarAcontecimientosDiarios(Model model) throws ParseException {
		
		List<Acontecimiento> listaAcontecimientos = acontecimientoServ.getAllAcontecimientos();
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaActual = formatter.parse(formatter.format(new Date()));
		
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				
		listaAcontecimientos = listaAcontecimientos.stream()
				.filter(a -> a.getFecha().after(fechaActual)
						/*&& auth.getName().equals(a.getUsuario().getUsuario())*/)
				.collect(Collectors.toList());
		
		Acontecimiento acont = new Acontecimiento(); 
		
		model.addAttribute("listaAcontecimientos", listaAcontecimientos);
		model.addAttribute("acont", acont);
		
		return "/views/acontecimiento/registrarAcontecimiento";
	}
	
	@PostMapping("/guardar")
	public String guardar(@Valid @ModelAttribute("acont") Acontecimiento acont,
						  BindingResult result,
						  RedirectAttributes atributos,
						  Model model) throws ParseException{
				
		if(result.hasErrors()) {
			
			List<Acontecimiento> listaAcontecimientos = acontecimientoServ.getAllAcontecimientos();			 
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date fechaActual = formatter.parse(formatter.format(new Date()));
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			
			listaAcontecimientos = listaAcontecimientos.stream()
					.filter(a -> (a.getFecha().after(fechaActual)
							&& auth.getName().equals(a.getUsuario().getUsuario())))
					.collect(Collectors.toList());
			
			model.addAttribute("listaAcontecimientos", listaAcontecimientos);			
			model.addAttribute("acont", acont);
			model.addAttribute("error", "No se pudo registrar el acontecimiento");
			
			System.out.println("Formulario incorrecto");
			System.out.println(result.toString());
			
			return "/views/acontecimiento/registrarAcontecimiento";
			
		}
		
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			
			acont.setUsuario(usuarioServ.findByUsuario(auth.getName()));
			
			acont.setFecha(new Date());
			acontecimientoServ.crearAcontecimiento(acont);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		atributos.addFlashAttribute("success", "Acontecimiento registrado exitosamente!");
		return "redirect:/views/acontecimiento";
	}
	
	
	@GetMapping("/previos")
	public String listarAcontecimientosAnteriores(Model model,
												  @RequestParam(name = "fechaAcontecimiento") String fechaAcontecimiento) throws ParseException {
		
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaAux = formatter.parse(fechaAcontecimiento);
		Date fechaAuxTomorrow = new Date(fechaAux.getTime() + (1000 * 60 * 60 * 24));
		
		
		List<Acontecimiento> listaAcontecimientos = acontecimientoServ.getAllAcontecimientos();
		
		listaAcontecimientos = listaAcontecimientos
				.stream()
				.filter(ac -> ac.getFecha().after(fechaAux) && ac.getFecha().before(fechaAuxTomorrow))
				.collect(Collectors.toList());
				
		model.addAttribute("listaAcontecimientos", listaAcontecimientos);
		model.addAttribute("diaSeleccionado", fechaAux);
		
		return "/views/acontecimiento/verAcontecimientosAnteriores";
	}
	
}
