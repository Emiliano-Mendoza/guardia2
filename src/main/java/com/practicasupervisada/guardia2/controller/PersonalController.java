package com.practicasupervisada.guardia2.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.practicasupervisada.guardia2.service.PersonalService;
import com.practicasupervisada.guardia2.service.SectorTrabajoService;
import com.practicasupervisada.guardia2.domain.Personal;
import com.practicasupervisada.guardia2.domain.SectorTrabajo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/views/personal")
public class PersonalController {
	
	@Autowired
	private PersonalService personalServ;
	
	@Autowired
	private SectorTrabajoService sectorTrabajoServ;
	
	
	@GetMapping("/editar")
	public String listarPersonal(Model model) {
		
		List<Personal> listaPersonal = personalServ.getAllPersonal()
				.stream()
				.filter(p -> p.getNombre() !=null
						&& p.getApellido() !=null
						&& p.getSectorTrabajo() != null)
				.collect(Collectors.toList());
		Collections.sort(listaPersonal);
		
		model.addAttribute("listaPersonal", listaPersonal);
		
		List<SectorTrabajo> listaSectores = sectorTrabajoServ.getAllSectorTrabajo()
				.stream()
				.filter(s -> s.getEnabled())
				.collect(Collectors.toList());
		
		model.addAttribute("listaSectores", listaSectores);
		
		
		return "/views/personal/editarPersonal";
	}
	
	
	@GetMapping("/agregar")
	public String agregarPersonal(Model model) {
		
		Personal personal = new Personal();
		
		model.addAttribute("personal", personal);
		
		List<SectorTrabajo> listaSectores = sectorTrabajoServ.getAllSectorTrabajo()
				.stream()
				.filter(s -> s.getEnabled())
				.collect(Collectors.toList());
		
		model.addAttribute("listaSectores", listaSectores);
		
		return "/views/personal/agregar";
	}
	
	@PostMapping("/guardar")
	public String guardar(@Valid @ModelAttribute Personal personal,
							BindingResult result,
							Model model,
							@RequestParam(name = "file") MultipartFile imagen,
							RedirectAttributes atributos){
		
		
		if(!personalServ.findById(personal.getNroLegajo()).isEmpty()) {
			//agrego un mensaje de error para el número de legajo repetido
			result.rejectValue("nroLegajo", "error.personal", "Número de legajo existente");
		} 
		
		if(result.hasErrors()) {
			
			model.addAttribute("personal", personal);
			model.addAttribute("error", "No se pudo crear el nuevo empleado");
			
			List<SectorTrabajo> listaSectores = sectorTrabajoServ.getAllSectorTrabajo()
					.stream()
					.filter(s -> s.getEnabled())
					.collect(Collectors.toList());
			
			model.addAttribute("listaSectores", listaSectores);
			
			
			return "/views/personal/agregar";
			
		}	
		
		//trato la imagen
		if(!imagen.isEmpty()) {
			// Path directorioImagenes = Paths.get("src//main//resources//static/images");
			// String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
			String rutaAbsoluta = "C://Guardia//Personal//recursos";
			
			try {
				byte[] bytesImg = imagen.getBytes();
				Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
				Files.write(rutaCompleta, bytesImg);
				
				personal.setImagen(imagen.getOriginalFilename());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			personal.setImagen("sinImagen");
		}
		
		try {
			personal.setEnabled(true);
			personalServ.crearPersonal(personal);
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		atributos.addFlashAttribute("success", "Empleado creado exitosamente!");
		return "redirect:/views/personal/agregar";
	}
	
	@PostMapping("/editar/{nroLegajo}")
	String editarPersonal(@PathVariable("nroLegajo") int nroLegajo,
						  @RequestParam(name = "nombre") String nombre,
						  @RequestParam(name = "apellido") String apellido,
						  @RequestParam(name = "sector") Integer sector,
						  @RequestParam(name = "enabled" , required = false) String enabled,
						  Model model,
						  @RequestParam(name = "file") MultipartFile imagen,
						  RedirectAttributes atributos) {
				
		Personal empleado = personalServ.findById(nroLegajo).get();
		
		if(nombre == null || apellido == null || sector == null 
				|| nombre.length()==0 || apellido.length()==0 ){			
						
			List<Personal> listaPersonal = personalServ.getAllPersonal();	
			Collections.sort(listaPersonal);
			
			model.addAttribute("listaPersonal", listaPersonal);
			model.addAttribute("error", "Datos incompletos. No se pudo editar el empleado");
			
			return "/views/personal/editarPersonal";
		}
				
		if(!imagen.isEmpty()) {

			String rutaAbsoluta = "C://Guardia//Personal//recursos";
			
			try {

				byte[] bytesImg = imagen.getBytes();
				Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
				Files.write(rutaCompleta, bytesImg);
				
				empleado.setImagen(imagen.getOriginalFilename());
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
				
		try {
			
			if(enabled == null) {
				empleado.setEnabled(false);
			}else if (enabled.equalsIgnoreCase("true")) {
				empleado.setEnabled(true);
			}
			
			empleado.setNombre(nombre);
			empleado.setApellido(apellido);
			
			SectorTrabajo sectorTrabajo = sectorTrabajoServ.findById(sector).orElseThrow();			
			empleado.setSectorTrabajo(sectorTrabajo);
			
			personalServ.crearPersonal(empleado);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
			
		
		atributos.addFlashAttribute("success", "Empleado editado exitosamente!");
		return "redirect:/views/personal/editar";
	}
	
}
