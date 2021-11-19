package com.practicasupervisada.guardia2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.practicasupervisada.guardia2.domain.Acontecimiento;
import com.practicasupervisada.guardia2.service.AcontecimientoService;

@Controller
@RequestMapping("/views/acontecimiento")
public class AcontecimientoController {
	
	@Autowired
	private AcontecimientoService acontecimientoServ;
	
	@GetMapping
	public String listarAcontecimientosDiarios(Model model) throws ParseException {
		
		List<Acontecimiento> listaAcontecimientos = acontecimientoServ.getAllAcontecimientos();
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaActual = formatter.parse(formatter.format(new Date()));
		System.out.println(fechaActual);
		
		listaAcontecimientos = listaAcontecimientos.stream()
				.filter(a -> a.getFecha().after(fechaActual))
				.collect(Collectors.toList());
		
		Acontecimiento acont = new Acontecimiento(); 
		
		model.addAttribute("listaAcontecimientos", listaAcontecimientos);
		model.addAttribute("acont", acont);
		
		return "/views/acontecimiento/registrarAcontecimiento";
	}
	
	@PostMapping("/guardar")
	public String guardar(@Valid @ModelAttribute("acont") Acontecimiento acont, BindingResult result, Model model) throws ParseException{
				
		if(result.hasErrors()) {
			
			List<Acontecimiento> listaAcontecimientos = acontecimientoServ.getAllAcontecimientos();			 
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date fechaActual = formatter.parse(formatter.format(new Date()));
			System.out.println(fechaActual);
			
			listaAcontecimientos = listaAcontecimientos.stream()
					.filter(a -> a.getFecha().after(fechaActual))
					.collect(Collectors.toList());
			
			model.addAttribute("listaAcontecimientos", listaAcontecimientos);			
			model.addAttribute("acont", acont);
			
			System.out.println("Formulario incorrecto");
			System.out.println(result.toString());
			
			return "/views/acontecimiento/registrarAcontecimiento";
			
		}
		
		try {
			acont.setFecha(new Date());
			acontecimientoServ.crearAcontecimiento(acont);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return "redirect:/views/acontecimiento";
	}
}
