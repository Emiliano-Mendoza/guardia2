package com.practicasupervisada.guardia2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.practicasupervisada.guardia2.domain.RetiroMaterial;
import com.practicasupervisada.guardia2.service.RetiroMaterialService;

@Controller
@RequestMapping("/views/retiro-material")
public class RetiroMaterialController {
	
	@Autowired
	private RetiroMaterialService retiroServ;
	
	@GetMapping
	public String obetenerRetirosDeMaterial(Model model) {
		try {
			List<RetiroMaterial> listaRetiros = retiroServ.getAllRetiroMaterial();
			
			model.addAttribute("retiro", listaRetiros);
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		
		//falta implementar
		return null;
	}
	
	@PostMapping("/autorizacion")
	public String nuevaAutorizacionDeRetiro(){
		
		return null;
	}
}
