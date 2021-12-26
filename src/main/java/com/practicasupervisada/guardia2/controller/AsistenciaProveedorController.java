package com.practicasupervisada.guardia2.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.practicasupervisada.guardia2.domain.AsistenciaProveedor;
import com.practicasupervisada.guardia2.domain.Proveedor;
import com.practicasupervisada.guardia2.service.AsistenciaProveedorService;
import com.practicasupervisada.guardia2.service.ProveedorService;
import com.practicasupervisada.guardia2.service.UsuarioService;

@Controller
@RequestMapping("/views/asistencia-proveedor")
public class AsistenciaProveedorController {
	
	@Autowired
	private AsistenciaProveedorService asistenciaServ;
		
	@Autowired
	private UsuarioService usuarioServ;
		
	@Autowired
	private ProveedorService proveedorServ;
	
	@GetMapping
	public String asistenciaProveedor(Model model) {
		
		List<Proveedor> listaProveedor = proveedorServ.getAllProveedor();
	
		List<AsistenciaProveedor> listaAsistencias = asistenciaServ.getAllAsistencias();
		List<AsistenciaProveedor> AsisSinEgreso = listaAsistencias
				.stream()
				.filter(a -> a.getSalida() == null && a.getProveedor()!=null)
				.collect(Collectors.toList());
		
		List<Proveedor> proveedorSinEgresar = new ArrayList <Proveedor> ();
		AsisSinEgreso.stream().forEach(a -> proveedorSinEgresar.add(a.getProveedor()));
		
		proveedorSinEgresar.stream().forEach(p -> {listaProveedor.remove(p);});
		
		model.addAttribute("listaProveedor", listaProveedor);
		model.addAttribute("todoProveedor", proveedorServ.getAllProveedor());
		
		
		
		return "/views/asistencia-proveedor/ingresoProveedor";
	}
	
	@GetMapping("/egreso")
	public String proveedoresIngresados(Model model) {
		
		List<AsistenciaProveedor> listaAsistencias = asistenciaServ.getAllAsistencias();
		List<AsistenciaProveedor> AsisSinEgreso = listaAsistencias
										.stream()
										.filter(a -> a.getSalida() == null  && a.getProveedor()!=null)
										.collect(Collectors.toList());		
		
		model.addAttribute("asistencias", AsisSinEgreso);
		
		return "/views/asistencia-proveedor/egresoProveedor";
	}
	
	@PostMapping("/ingreso/{idProveedor}")
	public String nuevaAsistenciaProveedor(@PathVariable("idProveedor") int idProveedor,
										  @RequestParam(name = "nombreChofer", required = false) String nombreChofer,
										  @RequestParam(name = "patenteVehiculo", required = false) String patenteVehiculo,
										  RedirectAttributes atributos) {
			
		try {
			Proveedor proveedor = proveedorServ.findById(idProveedor).orElseThrow();
			
			AsistenciaProveedor asis = new AsistenciaProveedor();
			asis.setProveedor(proveedor);
			asis.setEntrada(new Date());
			
			asis.setNombreChofer(nombreChofer);
			asis.setPatenteVehiculo(patenteVehiculo);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			asis.setUsuarioIngreso(usuarioServ.findByUsuario(auth.getName()));
			asistenciaServ.crearAsistencia(asis);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		atributos.addFlashAttribute("success", "Ingreso registrado exitosamente!");
		return "redirect:/views/asistencia-proveedor";
	}
	
	@PostMapping("/egreso/{idAsistencia}")
	public String egresoProveedor(@PathVariable("idAsistencia") int idAsistencia,
										  RedirectAttributes atributos) {
			
		try {
			AsistenciaProveedor asis = asistenciaServ.findById(idAsistencia).orElseThrow();
			
			asis.setSalida(new Date());
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			asis.setUsuarioEgreso(usuarioServ.findByUsuario(auth.getName()));
			
			asistenciaServ.crearAsistencia(asis);
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		atributos.addFlashAttribute("success", "Egreso registrado exitosamente!");
		return "redirect:/views/asistencia-proveedor/egreso";
	}
	
	@GetMapping("/previas")
	public String asistenciasPrevias(Model model,
						@RequestParam(name = "idProveedor") int idProveedor,
						@RequestParam(name = "date_range", required = false) String date_range) throws ParseException {
		
		String[] parts = date_range.split("-");
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaInicioAux = formatter.parse(parts[0]);
		Date fechaFinalAux = formatter.parse(parts[1]);
		
		List <AsistenciaProveedor> listaAsistencias = asistenciaServ.findAllByOrderByEntradaAsc();
		
		if(idProveedor > 0) {
			listaAsistencias = listaAsistencias.stream()
					.filter(a -> a.getEntrada().after(fechaInicioAux)
							&& a.getEntrada().before(fechaFinalAux)
							&& a.getProveedor().getIdProveedor() == idProveedor)
					.collect(Collectors.toList());	
		}
		else {
			listaAsistencias = listaAsistencias.stream()
					.filter(a -> a.getEntrada().after(fechaInicioAux)
							&& a.getEntrada().before(fechaFinalAux))
					.collect(Collectors.toList());	
		}
		
		
		model.addAttribute("listaAsistencias", listaAsistencias);
		
		return "/views/asistencia-proveedor/verAsistenciaProveedores";
	}
}
