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

import com.practicasupervisada.guardia2.service.AsistenciaService;
import com.practicasupervisada.guardia2.service.MaterialService;
import com.practicasupervisada.guardia2.service.PersonalService;
import com.practicasupervisada.guardia2.domain.Asistencia;
import com.practicasupervisada.guardia2.domain.Material;
import com.practicasupervisada.guardia2.domain.Personal;
import com.practicasupervisada.guardia2.util.FileUploadUtil;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;


@Controller
@RequestMapping("/views/personal")
public class PersonalController {
	
	@Autowired
	private PersonalService personalServ;
	@Autowired
	private AsistenciaService asistenciaServ;
	@Autowired
	private MaterialService materialServ;
	
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
	
	@GetMapping("/editar")
	public String listarPersonal(Model model) {
		
		List<Personal> listaPersonal = personalServ.getAllPersonal();	
		Collections.sort(listaPersonal);
		
		model.addAttribute("listaPersonal", listaPersonal);
		
		return "/views/personal/editarPersonal";
	}
	
	
	@GetMapping("/autorizar-retiro")
	public String listarClientesParaRetiroMaterial(Model model) {
		
		List<Personal> listaPersonal = personalServ.getAllPersonal();		
		Collections.sort(listaPersonal);
		
		List<Material> listaMateriales = materialServ.getAllMaterial();
				
		model.addAttribute("personal", listaPersonal);
		model.addAttribute("listaMateriales", listaMateriales);
		
		return "/views/retiro-material/autorizacion";
	}
	
	@GetMapping("/agregar")
	public String agregarPersonal(Model model) {
		
		Personal personal = new Personal();
		
		model.addAttribute("personal", personal);
		
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
			
			System.out.println("Formulario incorrecto");
			System.out.println(result.toString());
			
			
			return "/views/personal/agregar";
			
		}	
		
		//trato la imagen
		if(!imagen.isEmpty()) {
			// Path directorioImagenes = Paths.get("src//main//resources//static/images");
			// String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
			String rutaAbsoluta = "C://Personal//recursos";
			
			try {
				byte[] bytesImg = imagen.getBytes();
				Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
				Files.write(rutaCompleta, bytesImg);
				
				personal.setImagen(imagen.getOriginalFilename());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {		
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
						  @RequestParam(name = "sector") String sector,
						  Model model,
						  @RequestParam(name = "file") MultipartFile imagen,
						  RedirectAttributes atributos) {
		
		
		Personal empleado = personalServ.findById(nroLegajo).get();
		
		if(nombre == null || apellido == null || sector == null 
				|| nombre.length()==0 || apellido.length()==0 || sector.length()==0){			
						
			List<Personal> listaPersonal = personalServ.getAllPersonal();	
			Collections.sort(listaPersonal);
			
			model.addAttribute("listaPersonal", listaPersonal);
			model.addAttribute("error", "No se pudo editar el empleado");
			
			return "/views/personal/editarPersonal";
		}
				
		if(!imagen.isEmpty()) {

			String rutaAbsoluta = "C://Personal//recursos";
			
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
			empleado.setNombre(nombre);
			empleado.setApellido(apellido);
			empleado.setSector(sector);
			
			personalServ.crearPersonal(empleado);
		}catch (Exception e) {
			e.printStackTrace();
		}
			
		
		atributos.addFlashAttribute("success", "Empleado editado exitosamente!");
		return "redirect:/views/personal/editar";
	}
	
}
