package com.practicasupervisada.guardia2.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.practicasupervisada.guardia2.domain.Proveedor;
import com.practicasupervisada.guardia2.domain.Vehiculo;
import com.practicasupervisada.guardia2.service.ProveedorService;

@Controller
@RequestMapping("/views/proveedor")
public class ProveedorController {

	@Autowired
	private ProveedorService proveedorServ;
	
	@GetMapping("/agregar")
	public String agregarProveedor(Model model) {
		
		Proveedor proveedor = new Proveedor();
		
		model.addAttribute("proveedor", proveedor);
		
		return "/views/proveedor/agregarProveedor";
	}
	
	@GetMapping("/editar")
	public String editar(Model model) {
		
		List<Proveedor> listaProveedor = proveedorServ.getAllProveedor();
		
		model.addAttribute("listaProveedor", listaProveedor);
		
		return "/views/proveedor/editarProveedor";
	}
	
	@PostMapping("/guardar")
	public String guardar(@Valid @ModelAttribute Proveedor proveedor,
							BindingResult result,
							Model model,
							RedirectAttributes atributos){
		
		
		if(proveedorServ.existsByRazonSocial(proveedor.getRazonSocial())) {
			result.rejectValue("razonSocial", "error.proveedor", "Razón social existente");
		}
		
		
		if(result.hasErrors()) {
			
			model.addAttribute("proveedor", proveedor);
			model.addAttribute("error", "No se pudo crear el nuevo proveedor");
			
			System.out.println("Formulario incorrecto");			
			
			return "/views/proveedor/agregarProveedor";
			
		}
				
		try {		
			proveedorServ.crearProveedor(proveedor);			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		atributos.addFlashAttribute("success", "Proveedor cargado exitosamente!");
		return "redirect:/views/proveedor/agregar";
	}

	@PostMapping("/editar/{idProveedor}")
	public String editar(@PathVariable("idProveedor") int idProveedor,
						 @RequestParam(name = "razonSocial") String razonSocial,
						 Model model,
						 RedirectAttributes atributos){
		
		Proveedor proveedor = proveedorServ.findById(idProveedor).get();
		
		if(razonSocial.length()==0 || proveedor == null) {
			
			List<Proveedor> listaProveedor = proveedorServ.getAllProveedor();
			
			model.addAttribute("listaProveedor", listaProveedor);
			
			model.addAttribute("error", "No se pudo editar el proveedor");
			
			System.out.println("Formulario incorrecto");			
			
			return "/views/proveedor/editarProveedor";
		}
		
				
		try {
			proveedor.setRazonSocial(razonSocial);
			
			
			proveedorServ.crearProveedor(proveedor);
			System.out.println("Proveedor editado correctamente.");
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		atributos.addFlashAttribute("success", "Proveedor editado exitosamente!");
		return "redirect:/views/proveedor/editar";
	}
}
