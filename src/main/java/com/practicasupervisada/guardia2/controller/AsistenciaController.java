package com.practicasupervisada.guardia2.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.practicasupervisada.guardia2.domain.Asistencia;
import com.practicasupervisada.guardia2.domain.Personal;
import com.practicasupervisada.guardia2.service.AsistenciaService;
import com.practicasupervisada.guardia2.service.PersonalService;

@Controller
@RequestMapping("/views/asistencia")
public class AsistenciaController {
	
	@Autowired
	private AsistenciaService asistenciaServ;
	
	@Autowired
	private PersonalService personalServ;
	
	@GetMapping
	public String index() {
		return "home";
	}
	
	@GetMapping("/empleado/{nroLegajo}")
	public String mostrarNuevaAsistencia(@PathVariable("nroLegajo") int nroLegajo, Model model) throws Exception{
		
		try {
			Personal personal = personalServ.findById(nroLegajo).orElseThrow();
			System.out.println(personal.toString());
			model.addAttribute("personal", personal);
		}catch(Exception e) {
			return "home";
		}
		
		return "/views/asistencia/nuevaAsistencia";
	}
	
	@PostMapping("/ingreso-empleado/{nroLegajo}")
	public String agregarNuevaAsistencia(@PathVariable("nroLegajo") int nroLegajo) {
			
		try {
			Personal personal = personalServ.findById(nroLegajo).orElseThrow();
			System.out.println(personal.toString());
			
			Asistencia asis = new Asistencia();
			asis.setPersonal(personal);
			asis.setEntrada(new Date());
			asis.setEnTransito(false);
			//asis.setUsuario(null);
			asistenciaServ.crearAsistencia(asis);
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		
		return "redirect:/views/personal";
	}
	
	@GetMapping("/personal-ingresado")
	public String mostrarAsistenciasEmpleadosIngresados(Model model){
		
		try {
			List<Asistencia> listaAsistencias = asistenciaServ.getAllAsistencias();
			List<Asistencia> AsisSinEgreso = listaAsistencias
											.stream()
											.filter(a -> a.getSalida() == null)
											.collect(Collectors.toList());
			
			model.addAttribute("asistencias", AsisSinEgreso);
			
		}catch(Exception e) {
			return "home";
		}
		
		return "/views/asistencia/egresoPersonal";
	}
	
	@PostMapping("/egreso-empleado/{idAsistencia}")
	public String egreso(@PathVariable("idAsistencia") int idAsistencia) {
			
		try {
			Asistencia asis = asistenciaServ.findById(idAsistencia).orElseThrow();
			
			asis.setSalida(new Date());
			asis.setEnTransito(false);
			asistenciaServ.crearAsistencia(asis);
			
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		
		return "redirect:/views/asistencia/personal-ingresado";
	}
}
