package com.practicasupervisada.guardia2.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.practicasupervisada.guardia2.service.AsistenciaService;
import com.practicasupervisada.guardia2.service.PersonalService;
import com.practicasupervisada.guardia2.domain.Asistencia;
import com.practicasupervisada.guardia2.domain.Personal;
import com.practicasupervisada.guardia2.util.FileUploadUtil;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Base64;


@Controller
@RequestMapping("/views/personal")
public class PersonalController {
	
	@Autowired
	private PersonalService personalServ;
	@Autowired
	private AsistenciaService asistenciaServ;
	
	@GetMapping
	public String listarClientes(Model model) {
		
		List<Personal> listaPersonal = personalServ.getAllPersonal();
		
		Collections.sort(listaPersonal);
		// Busco la lista de asistencias sin egreso actual
		List<Asistencia> listaAsistencias = asistenciaServ.getAllAsistencias();
		List<Asistencia> AsisSinEgreso = listaAsistencias
										.stream()
										.filter(a -> a.getSalida() == null)
										.collect(Collectors.toList());
		
		// Busco al personal que aun no ha egresado
		List<Personal> personalSinEgresar = new ArrayList <Personal> ();
		AsisSinEgreso.stream().forEach(a -> personalSinEgresar.add(a.getPersonal()));
		
		// Remuevo al personal sin egresar de la lista 
		personalSinEgresar.stream().forEach(p -> {listaPersonal.remove(p);});
		
		
		model.addAttribute("personal", listaPersonal);
		
		return "/views/personal/listar";
	}
	
	@GetMapping("/agregar")
	public String agregarPersonal(Model model) {
		
		Personal personal = new Personal();
		
		model.addAttribute("personal", personal);
		
		return "/views/personal/agregar";
	}
	
	@PostMapping("/guardar")
	public String guardar(@Valid @ModelAttribute Personal personal, BindingResult result, Model model){
		
		if(!personalServ.findById(personal.getNroLegajo()).isEmpty()) {
			//agrego un mensaje de error para el número de legajo repetido
			result.rejectValue("nroLegajo", "error.personal", "Número de legajo existente");
		} 
		
		if(result.hasErrors()) {
			
			model.addAttribute("personal", personal);
			
			System.out.println("Formulario incorrecto");
			System.out.println(result.toString());
			
			return "/views/personal/agregar";
			
		}
		
		
		try {		
			personalServ.crearPersonal(personal);
		}catch(Exception e) {
			return "error";
		}
		
		return "redirect:/views/personal";
	}
	
}
