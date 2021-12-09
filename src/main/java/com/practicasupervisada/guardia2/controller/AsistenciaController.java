package com.practicasupervisada.guardia2.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.practicasupervisada.guardia2.domain.Asistencia;
import com.practicasupervisada.guardia2.domain.Personal;
import com.practicasupervisada.guardia2.domain.Transito;
import com.practicasupervisada.guardia2.service.AsistenciaService;
import com.practicasupervisada.guardia2.service.PersonalService;
import com.practicasupervisada.guardia2.service.UsuarioService;

@Controller
@RequestMapping("/views/asistencia")
public class AsistenciaController {
	
	@Autowired
	private AsistenciaService asistenciaServ;
	
	@Autowired
	private PersonalService personalServ;
	
	@Autowired
	private UsuarioService usuarioServ;
	
	@GetMapping
	public String index() {
		return "home";
	}
	
	/* OBSOLETO
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
	*/
	
	@PostMapping("/ingreso-empleado/{nroLegajo}")
	public String agregarNuevaAsistencia(@PathVariable("nroLegajo") int nroLegajo,
										  RedirectAttributes atributos) {
			
		try {
			Personal personal = personalServ.findById(nroLegajo).orElseThrow();
			System.out.println(personal.toString());
			
			Asistencia asis = new Asistencia();
			asis.setPersonal(personal);
			asis.setEntrada(new Date());
			asis.setEnTransito(false);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			asis.setUsuarioIngreso(usuarioServ.findByUsuario(auth.getName()));
			asistenciaServ.crearAsistencia(asis);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		atributos.addFlashAttribute("success", "Ingreso registrado exitosamente!");
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
	public String egreso(@PathVariable("idAsistencia") int idAsistencia,
						RedirectAttributes atributos) {
			
		try {
			Asistencia asis = asistenciaServ.findById(idAsistencia).orElseThrow();
			
			asis.setSalida(new Date());
			asis.setEnTransito(false);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			asis.setUsuarioEgreso(usuarioServ.findByUsuario(auth.getName()));
			
			asistenciaServ.crearAsistencia(asis);
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		atributos.addFlashAttribute("success", "Egreso registrado exitosamente!");
		return "redirect:/views/asistencia/personal-ingresado";
	}
	
	@PostMapping("/egreso-transitorio/{idAsistencia}")
	public String egresoTransitorio(@PathVariable("idAsistencia") int idAsistencia,
						RedirectAttributes atributos) {
			
		try {
			Asistencia asis = asistenciaServ.findById(idAsistencia).orElseThrow();
			
			Transito transito = new Transito();
			transito.setFechaSalidaTransitoria(new Date());
			transito.setAsistencia(asis);
			transito.setPersonal(asis.getPersonal());
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			transito.setUsuarioEgreso(usuarioServ.findByUsuario(auth.getName()));
			
			asis.getTransito().add(transito);
			asis.setEnTransito(true);
			
			asistenciaServ.crearAsistencia(asis);

			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		atributos.addFlashAttribute("success", "Egreso transitorio registrado!");
		return "redirect:/views/asistencia/personal-ingresado";
	}
	
	@PostMapping("/reingreso-transitorio/{idAsistencia}")
	public String reIngresoTransitorio(@PathVariable("idAsistencia") int idAsistencia,
						RedirectAttributes atributos) {
			
		try {
			Asistencia asis = asistenciaServ.findById(idAsistencia).orElseThrow();
			
			Transito transito = asis.getTransito().
					stream().
					filter(t -> (t.getFechaReingreso()==null && t.getUsuarioIngreso()==null)).
					findFirst().orElseThrow();
			
			transito.setFechaReingreso(new Date());
			transito.setPersonal(asis.getPersonal());
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			transito.setUsuarioIngreso(usuarioServ.findByUsuario(auth.getName()));
			

			asis.setEnTransito(false);
			
			asistenciaServ.crearAsistencia(asis);

			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		atributos.addFlashAttribute("success", "Reingreso transitorio registrado!");
		return "redirect:/views/asistencia/personal-ingresado";
	}
}
