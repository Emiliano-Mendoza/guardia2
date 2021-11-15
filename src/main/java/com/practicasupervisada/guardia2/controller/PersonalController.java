package com.practicasupervisada.guardia2.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

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

import com.practicasupervisada.guardia2.service.PersonalService;
import com.practicasupervisada.guardia2.domain.Personal;
import com.practicasupervisada.guardia2.util.FileUploadUtil;

import org.springframework.util.StringUtils;
import java.util.Base64;


@Controller
@RequestMapping("/views/personal")
public class PersonalController {
	
	@Autowired
	private PersonalService personalServ;
	
	@GetMapping
	public String listarClientes(Model model) {
		
		List<Personal> listaPersonal = personalServ.getAllPersonal();
		
		Collections.sort(listaPersonal);
		
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
