package com.practicasupervisada.guardia2.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.practicasupervisada.guardia2.domain.Personal;
import com.practicasupervisada.guardia2.domain.Vehiculo;
import com.practicasupervisada.guardia2.service.VehiculoService;

@Controller
@RequestMapping("/views/vehiculo")
public class VehiculoController {
	
	@Autowired
	private VehiculoService vehiculoServ;
	
	@GetMapping("/agregar")
	public String agregarVehiculo(Model model) {
		
		Vehiculo vehiculo = new Vehiculo();
		
		model.addAttribute("vehiculo", vehiculo);
		
		return "/views/vehiculo/agregar_vehiculo";
	}
	
	@GetMapping("/editar")
	public String listarVehiculo(Model model) {
		
		List<Vehiculo> listaVehiculo = vehiculoServ.getAllVehiculo();	
		
		model.addAttribute("listaVehiculo", listaVehiculo);
		
		return "/views/personal/editar_vehiculo";
	}
	
	@PostMapping("/guardar")
	public String guardar(@Valid @ModelAttribute Vehiculo vehiculo,
							BindingResult result,
							Model model,
							RedirectAttributes atributos){
		
		if(result.hasErrors()) {
			
			model.addAttribute("vehiculo", vehiculo);
			model.addAttribute("error", "No se pudo crear el nuevo vehiculo");
			
			System.out.println("Formulario incorrecto");			
			
			return "/views/vehiculo/agregar_vehiculo";
			
		}
				
		try {		
			vehiculoServ.crearVehiculo(vehiculo);			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		atributos.addFlashAttribute("success", "Vehiculo cargado exitosamente!");
		return "redirect:/views/vehiculo/agregar";
	}
}
