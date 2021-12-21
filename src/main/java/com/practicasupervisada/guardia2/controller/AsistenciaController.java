package com.practicasupervisada.guardia2.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.practicasupervisada.guardia2.domain.Asistencia;
import com.practicasupervisada.guardia2.domain.Personal;
import com.practicasupervisada.guardia2.domain.Proveedor;
import com.practicasupervisada.guardia2.domain.Transito;
import com.practicasupervisada.guardia2.domain.Vehiculo;
import com.practicasupervisada.guardia2.service.AsistenciaService;
import com.practicasupervisada.guardia2.service.PersonalService;
import com.practicasupervisada.guardia2.service.ProveedorService;
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
	private ProveedorService proveedorServ;
	
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
		return "redirect:/views/asistencia/personal/egreso";
	}
	
	@PostMapping("/egreso-transitorio/{idAsistencia}")
	public String egresoTransitorio(@PathVariable("idAsistencia") int idAsistencia,
						@RequestParam(name = "vehiculo") int idVehiculo,
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
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			transito.setUsuarioEgreso(usuarioServ.findByUsuario(auth.getName()));
			
			asis.getTransito().add(transito);
			asis.setEnTransito(true);
			
			asistenciaServ.crearAsistencia(asis);

			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		atributos.addFlashAttribute("success", "Egreso transitorio registrado!");
		return "redirect:/views/asistencia/personal/egreso";
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
		return "redirect:/views/asistencia/personal/egreso";
	}
	
	
	@GetMapping("/proveedor")
	public String asistenciaProveedor(Model model) {
		
		List<Proveedor> listaProveedor = proveedorServ.getAllProveedor();
		
		List<Asistencia> listaAsistencias = asistenciaServ.getAllAsistencias();
		List<Asistencia> AsisSinEgreso = listaAsistencias
				.stream()
				.filter(a -> a.getSalida() == null && a.getProveedor()!=null && a.getPersonal()==null)
				.collect(Collectors.toList());
		
		List<Proveedor> proveedorSinEgresar = new ArrayList <Proveedor> ();
		AsisSinEgreso.stream().forEach(a -> proveedorSinEgresar.add(a.getProveedor()));
		
		proveedorSinEgresar.stream().forEach(p -> {listaProveedor.remove(p);});
		
		model.addAttribute("listaProveedor", listaProveedor);		
		return "/views/asistencia/ingresoProveedor";
	}
	
	@GetMapping("/proveedor/egreso")
	public String proveedoresIngresados(Model model) {
		
		List<Asistencia> listaAsistencias = asistenciaServ.getAllAsistencias();
		List<Asistencia> AsisSinEgreso = listaAsistencias
										.stream()
										.filter(a -> a.getSalida() == null && a.getPersonal()==null && a.getProveedor()!=null)
										.collect(Collectors.toList());		
		
		model.addAttribute("asistencias", AsisSinEgreso);
		
		return "/views/asistencia/egresoProveedor";
	}
	
	
	@PostMapping("/proveedor/ingreso/{idProveedor}")
	public String nuevaAsistenciaProveedor(@PathVariable("idProveedor") int idProveedor,
										  RedirectAttributes atributos) {
			
		try {
			Proveedor proveedor = proveedorServ.findById(idProveedor).orElseThrow();
			
			Asistencia asis = new Asistencia();
			asis.setProveedor(proveedor);
			asis.setEntrada(new Date());
			asis.setEnTransito(false);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			asis.setUsuarioIngreso(usuarioServ.findByUsuario(auth.getName()));
			asistenciaServ.crearAsistencia(asis);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		atributos.addFlashAttribute("success", "Ingreso registrado exitosamente!");
		return "redirect:/views/asistencia/proveedor";
	}
	
	
	@PostMapping("/proveedor/egreso/{idAsistencia}")
	public String egresoProveedor(@PathVariable("idAsistencia") int idAsistencia,
										  RedirectAttributes atributos) {
			
		try {
			Asistencia asis = asistenciaServ.findById(idAsistencia).orElseThrow();
			
			asis.setSalida(new Date());
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			asis.setUsuarioEgreso(usuarioServ.findByUsuario(auth.getName()));
			
			asistenciaServ.crearAsistencia(asis);
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		atributos.addFlashAttribute("success", "Egreso registrado exitosamente!");
		return "redirect:/views/asistencia/proveedor/egreso";
	}
	
	
	@GetMapping("/personal")
	public String listarClientes(Model model) {
		
		List<Personal> listaPersonal = personalServ.getAllPersonal()
																	.stream()
																	.filter(p -> p.getEnabled())
																	.collect(Collectors.toList());
		
		
		Collections.sort(listaPersonal);		
		// Busco la lista de asistencias sin egreso actual
		List<Asistencia> listaAsistencias = asistenciaServ.getAllAsistencias();
		List<Asistencia> AsisSinEgreso = listaAsistencias
										.stream()
										.filter(a -> a.getSalida() == null 
													&& a.getPersonal()!=null
													&& a.getProveedor()==null)
										.collect(Collectors.toList());
		
		// Busco al personal que aun no ha egresado
		List<Personal> personalSinEgresar = new ArrayList <Personal> ();
		AsisSinEgreso.stream().forEach(a -> personalSinEgresar.add(a.getPersonal()));
		
		// Remuevo al personal sin egresar de la lista 
		personalSinEgresar.stream().forEach(p -> {listaPersonal.remove(p);});
		
		
		model.addAttribute("personal", listaPersonal);
		
		return "/views/asistencia/ingresoPersonal";
	}
	
	@GetMapping("/personal/egreso")
	public String mostrarAsistenciasEmpleadosIngresados(Model model){
		
		try {
			List<Asistencia> listaAsistencias = asistenciaServ.getAllAsistencias();
			List<Asistencia> AsisSinEgreso = listaAsistencias
											.stream()
											.filter(a -> a.getSalida() == null && a.getPersonal()!=null && a.getProveedor()==null)
											.collect(Collectors.toList());
			
			List<Vehiculo> listaVehiculos = vehiculoServ.getAllVehiculo();
			
			model.addAttribute("asistencias", AsisSinEgreso);
			model.addAttribute("listaVehiculos", listaVehiculos);
			
		}catch(Exception e) {
			return "home";
		}
		
		return "/views/asistencia/egresoPersonal";
	}
}
