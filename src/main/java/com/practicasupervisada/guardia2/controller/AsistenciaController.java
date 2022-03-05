package com.practicasupervisada.guardia2.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.practicasupervisada.guardia2.domain.Asistencia;
import com.practicasupervisada.guardia2.domain.Personal;
import com.practicasupervisada.guardia2.domain.Transito;
import com.practicasupervisada.guardia2.domain.Vehiculo;
import com.practicasupervisada.guardia2.service.AsistenciaService;
import com.practicasupervisada.guardia2.service.PersonalService;
import com.practicasupervisada.guardia2.service.TransitoService;
import com.practicasupervisada.guardia2.service.UsuarioService;
import com.practicasupervisada.guardia2.service.VehiculoService;

@Controller
@RequestMapping("/views/asistencia")
public class AsistenciaController {
	
	@Autowired
	private AsistenciaService asistenciaServ;
	
	@Autowired
	private PersonalService personalServ;
	
	@Autowired
	private UsuarioService usuarioServ;
	
	@Autowired
	private VehiculoService vehiculoServ;
	
	@Autowired
	private TransitoService transitoServ;
	
	
	@GetMapping
	public String index() {
		return "home";
	}
	
	
	@PostMapping("/ingreso-empleado/{nroLegajo}")
	public String agregarNuevaAsistencia(@PathVariable("nroLegajo") int nroLegajo,										
										RedirectAttributes atributos) {
			
		try {
			Personal personal = personalServ.findById(nroLegajo).orElseThrow();
			
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
		return "redirect:/views/asistencia/personal";
	}
	

	
	@PostMapping("/egreso-empleado/{idAsistencia}")
	public String RegistrarEgresoDeAsistencia(@PathVariable("idAsistencia") int idAsistencia,
						RedirectAttributes atributos) {
			
		try {
			Asistencia asis = asistenciaServ.findById(idAsistencia).orElseThrow();
			
			asis.setSalida(new Date());
			asis.setEnTransito(false);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			asis.setUsuarioEgreso(usuarioServ.findByUsuario(auth.getName()));
			
			asistenciaServ.actualizarAsistencia(asis);
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		atributos.addFlashAttribute("success", "Egreso registrado exitosamente!");
		return "redirect:/views/asistencia/personal/egreso";
	}
	
	@PostMapping("/egreso-transitorio/{idAsistencia}")
	public String registrarSalidaTransitoria(@PathVariable("idAsistencia") int idAsistencia,
						@RequestParam(name = "vehiculo") int idVehiculo,
						@RequestParam(name = "comentario") String comentario,
						RedirectAttributes atributos) {
		
			
		try {
			Asistencia asis = asistenciaServ.findById(idAsistencia).orElseThrow();
			
			Transito transito = new Transito();
			
			if(idVehiculo != -1) {
				Vehiculo vehiculo = vehiculoServ.findById(idVehiculo).orElseThrow();
				transito.setVehiculo(vehiculo);				
			}			

			
			transito.setFechaSalidaTransitoria(new Date());
			transito.setAsistencia(asis);
			transito.setPersonal(asis.getPersonal());
			transito.setComentario(comentario);
			
			//transito.setTipoTransito("Egreso Transitorio");
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			transito.setUsuarioEgreso(usuarioServ.findByUsuario(auth.getName()));
			
			asis.setEnTransito(true);
			
			transitoServ.crearTransito(transito);
			asistenciaServ.actualizarAsistencia(asis);

			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		atributos.addFlashAttribute("success", "Egreso transitorio registrado!");
		return "redirect:/views/asistencia/personal/egreso";
	}
	
	//Tener en cuenta que sería mejor si le pasara el idTransito
	@PostMapping("/reingreso-transitorio/{idAsistencia}")
	public String reIngresoTransitorio(@PathVariable("idAsistencia") int idAsistencia,
						@RequestParam(name = "vehiculo") int idVehiculo,
						@RequestParam(name = "comentario") String comentario,
						RedirectAttributes atributos) {
			
		try {
			Asistencia asis = asistenciaServ.findById(idAsistencia).orElseThrow();
			Transito transito = transitoServ.getAllAsistencias()
					.stream()
					.filter(t -> (t.getAsistencia().getIdAsistencia() == idAsistencia && t.getFechaReingreso()==null && t.getUsuarioIngreso()==null))
					.findFirst().orElseThrow();

			transito.setFechaReingreso(new Date());
			
			if(idVehiculo != -1) {
				Vehiculo vehiculo = vehiculoServ.findById(idVehiculo).orElseThrow();
				transito.setVehiculo2(vehiculo);				
			}
						
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			transito.setUsuarioIngreso(usuarioServ.findByUsuario(auth.getName()));
			
			transito.setComentario2(comentario);
			
			asis.setEnTransito(false);
			
			transitoServ.actualizarTransito(transito);
			asistenciaServ.actualizarAsistencia(asis);

		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		atributos.addFlashAttribute("success", "Reingreso transitorio registrado!");
		return "redirect:/views/asistencia/personal/egreso";
	}

	@GetMapping("/personal")
	public String ListarEmpleadosHabilitadosSinIngresar(Model model) {
		
		List<Personal> listaPersonal = personalServ.getAllPersonal()
																	.stream()
																	.filter(p -> p.getEnabled() !=null 
																			&& p.getEnabled()
																			&& p.getNombre() !=null
																			&& p.getApellido() !=null
																			)
																	.collect(Collectors.toList());
		
		
		Collections.sort(listaPersonal);		
		// Busco la lista de asistencias sin egreso actual
		List<Asistencia> listaAsistencias = asistenciaServ.getAllAsistencias();
		List<Asistencia> AsisSinEgreso = listaAsistencias
										.stream()
										.filter(a -> a.getSalida() == null 
													&& a.getPersonal()!=null)
										.collect(Collectors.toList());
		
		// Busco al personal que aun no ha egresado
		List<Personal> personalSinEgresar = new ArrayList <Personal> ();
		AsisSinEgreso.stream().forEach(a -> personalSinEgresar.add(a.getPersonal()));
		
		// Remuevo al personal sin egresar de la lista 
		personalSinEgresar.stream().forEach(p -> {listaPersonal.remove(p);});
		
		
		model.addAttribute("personal", listaPersonal);
		
		List<Personal> todoPersonal = personalServ.findAllByOrderByApellidoAsc();
		
		model.addAttribute("todoPersonal", todoPersonal);
		
		
		
		return "/views/asistencia/ingresoPersonal";
	}
	
	@GetMapping("/personal/egreso")
	public String mostrarAsistenciasEmpleadosIngresados(Model model){
		
		try {
			List<Asistencia> listaAsistencias = asistenciaServ.findAllByOrderByEntradaAsc();
			List<Asistencia> AsisSinEgreso = listaAsistencias
											.stream()
											.filter(a -> a.getSalida() == null && a.getPersonal()!=null)
											.collect(Collectors.toList());
			
			List<Vehiculo> listaVehiculos = vehiculoServ.getAllVehiculo();
			
			model.addAttribute("asistencias", AsisSinEgreso);
			model.addAttribute("listaVehiculos", listaVehiculos);
			
			List<Personal> todoPersonal = personalServ.findAllByOrderByApellidoAsc()
					.stream()
					.filter(p -> p.getEnabled() !=null 
								&& p.getNombre() !=null
								&& p.getApellido() !=null
								)
			.collect(Collectors.toList());;
			
			model.addAttribute("todoPersonal", todoPersonal);
			
		}catch(Exception e) {
			return "home";
		}
		
		return "/views/asistencia/egresoPersonal";
	}
	
	
	@GetMapping("/asistencias-empleado/{nroLegajo}")
	public String asistenciasEmpleado(@PathVariable("nroLegajo") int nroLegajo,
						@RequestParam(name = "date_range", required = false) String date_range,
						Model model) throws ParseException {
		
		String[] parts = date_range.split("-");
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaInicioAux = formatter.parse(parts[0]);
		Date fechaFinalAux = new Date(formatter.parse(parts[1]).getTime() + (1000 * 60 * 60 * 24));
		
		List <Asistencia> listaAsistencias = asistenciaServ.findAllByOrderByEntradaAsc();
		listaAsistencias = listaAsistencias.stream()
				.filter(a ->  a.getEntrada() != null
						&& a.getPersonal() != null
						&& a.getUsuarioIngreso() != null
						&& a.getPersonal().getNroLegajo() == nroLegajo
						&& a.getEntrada().after(fechaInicioAux)
						&& a.getEntrada().before(fechaFinalAux))
				.collect(Collectors.toList());	
		
		model.addAttribute("listaAsistencias", listaAsistencias);
		
		return "/views/asistencia/verAsistencias";
	}
	
	@GetMapping("/previas")
	public String asistenciasPrevias(Model model,
						@RequestParam(name = "nroLegajo") int nroLegajo,
						@RequestParam(name = "date_range", required = false) String date_range) throws ParseException {
		
		String[] parts = date_range.split("-");
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaInicioAux = formatter.parse(parts[0]);
		Date fechaFinalAux = new Date(formatter.parse(parts[1]).getTime() + (1000 * 60 * 60 * 24));
		
		List <Asistencia> listaAsistencias = asistenciaServ.findAllByOrderByEntradaAsc();
		
		if(nroLegajo < 0) {			
			listaAsistencias = listaAsistencias.stream()
					.filter(a -> a.getEntrada() != null
							&& a.getPersonal() != null
							&& a.getUsuarioIngreso() != null
							&& a.getEntrada().after(fechaInicioAux)
							&& a.getEntrada().before(fechaFinalAux))
					.collect(Collectors.toList());
		}else {
			listaAsistencias = listaAsistencias.stream()
					.filter(a -> a.getEntrada() != null
							&& a.getPersonal() != null
							&& a.getUsuarioIngreso() != null 
							&& a.getEntrada().after(fechaInicioAux)
							&& a.getEntrada().before(fechaFinalAux)
							&& a.getPersonal().getNroLegajo() == nroLegajo)
					.collect(Collectors.toList());
		}
				
		model.addAttribute("listaAsistencias", listaAsistencias);
		
		return "/views/asistencia/verAsistencias";
	}
	
	@GetMapping("/salidas-transitorias")
	public String verSalidasTransitorias(Model model,
						@RequestParam(name = "nroLegajo") Integer nroLegajo,
						@RequestParam(name = "date_range") String date_range) throws ParseException {
		
		String[] parts = date_range.split("-");
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaInicioAux = formatter.parse(parts[0]);
		Date fechaFinalAux = new Date(formatter.parse(parts[1]).getTime() + (1000 * 60 * 60 * 24));
		List <Transito> listaTransito = transitoServ.findAllByOrderByFechaSalidaTransitoriaAsc();
		
		if(nroLegajo>0) {
			listaTransito = listaTransito.stream()
					.filter(t -> t.getFechaSalidaTransitoria() != null
								 && t.getUsuarioEgreso() != null
								 && t.getAsistencia() != null
								 && t.getFechaSalidaTransitoria().after(fechaInicioAux)
							     && t.getFechaSalidaTransitoria().before(fechaFinalAux)
							     && t.getPersonal() != null
							     && t.getPersonal().getNroLegajo() == nroLegajo)
					.collect(Collectors.toList());
		}else {
			listaTransito = listaTransito.stream()
					.filter(t -> t.getFechaSalidaTransitoria() != null
									 && t.getUsuarioEgreso() != null
									 && t.getAsistencia() != null
									 && t.getFechaSalidaTransitoria().after(fechaInicioAux)
									 && t.getFechaSalidaTransitoria().before(fechaFinalAux))
					.collect(Collectors.toList());
		}
				
		model.addAttribute("listaTransito", listaTransito);
		
		return "/views/asistencia/verTransitos";
	}
	
}
