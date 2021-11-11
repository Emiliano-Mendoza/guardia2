package com.practicasupervisada.guardia2.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.practicasupervisada.guardia2.service.PersonalService;
import com.practicasupervisada.guardia2.domain.Personal;


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
	
	
}
