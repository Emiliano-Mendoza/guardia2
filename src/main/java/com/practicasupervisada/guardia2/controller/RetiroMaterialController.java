package com.practicasupervisada.guardia2.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
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

import com.practicasupervisada.guardia2.domain.AsistenciaProveedor;
import com.practicasupervisada.guardia2.domain.Material;
import com.practicasupervisada.guardia2.domain.Personal;
import com.practicasupervisada.guardia2.domain.RetiroMaterial;
import com.practicasupervisada.guardia2.domain.Roles;
import com.practicasupervisada.guardia2.domain.Usuario;
import com.practicasupervisada.guardia2.service.MaterialService;
import com.practicasupervisada.guardia2.service.PersonalService;
import com.practicasupervisada.guardia2.service.RetiroMaterialService;
import com.practicasupervisada.guardia2.service.UsuarioService;

@Controller
@RequestMapping("/views/retiro-material")
public class RetiroMaterialController {
	
	@Autowired
	private RetiroMaterialService retiroServ;
	@Autowired
	private PersonalService personalServ;
	@Autowired
	private UsuarioService usuarioServ;
	@Autowired
	private MaterialService materialServ;
	
	@GetMapping
	public String obetenerRetirosDeMaterial(Model model) {
		try {
			List<RetiroMaterial> listaRetiros = retiroServ.getAllRetiroMaterial();
			
			//traigo solo aquellos retiros que no hayas sucedido y que la autorizacion aun esté vigente
			listaRetiros = listaRetiros.stream()
					.filter(ret -> (ret.getFechaRetiro()==null && ret.getFechaLimite().after(new Date())))
					.collect(Collectors.toList());
			
			List<Personal> listaPersonal = personalServ.findAllByOrderByApellidoAsc();
			
			model.addAttribute("listaRetiros", listaRetiros);
			model.addAttribute("listaPersonal", listaPersonal);
			
			//----------------------------
			
			List<Usuario> listaUsuarios = usuarioServ.getAllUsuario()
					.stream()
					.filter(u -> {
							Iterator<Roles> iterator = u.getRoles().iterator();
							Roles rol = new Roles();
							
							while(iterator.hasNext()){
								 rol = iterator.next();
								 if(rol.getRol().equals("AUTORIZANTE") || rol.getRol().equals("ADMIN")) return true;
								
							}
							return false;
						} 					
					)
					.collect(Collectors.toList());;	
					
			model.addAttribute("listaUsuarios", listaUsuarios);
			
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return "/views/retiro-material/retiro";
	}
	
	@GetMapping("/autorizar")
	public String listarClientesParaRetiroMaterial(Model model) {
		
		List<Personal> listaPersonal = personalServ.getAllPersonal()
				.stream()
				.filter(p -> p.getEnabled())
				.collect(Collectors.toList());	
		Collections.sort(listaPersonal);
		
		List<Material> listaMateriales = materialServ.getAllMaterial();
				
		model.addAttribute("personal", listaPersonal);
		model.addAttribute("listaMateriales", listaMateriales);
		
		return "/views/retiro-material/autorizacion";
	}
	
	@PostMapping("/autorizacion")
	public String nuevaAutorizacionDeRetiro(@RequestParam(name = "desc") String desc,
											@RequestParam(name = "fechaLimite") String fecha,
											@RequestParam(name = "nroLegajo") int nroLegajo,
											@RequestParam(name = "materiales") List<Integer> materiales,
											RedirectAttributes atributos){
		
		//Le doy el formato correcto a la fecha obtenida
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		

		try {
			
			RetiroMaterial retiro = new RetiroMaterial();
			
			if(nroLegajo > 0) {
				Personal empleado = personalServ.findById(nroLegajo).orElseThrow();
				retiro.setPersonal(empleado);
			}						
			
			retiro.setDescripcion(desc);
			
			Date fechaLimite = formatter.parse(fecha + " 23:59:59");
			retiro.setFechaLimite(fechaLimite);
			
			
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			retiro.setUsuarioSector(usuarioServ.findByUsuario(auth.getName()));
			
			retiroServ.crearRetiroMaterial(retiro, materiales);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		atributos.addFlashAttribute("success", "Autorización de retiro creada exitosamente!");
		return "redirect:/views/retiro-material/autorizar";
	}
	
	@PostMapping("/retiro/{idRetiro}")
	public String egreso(@PathVariable("idRetiro") int idRetiro, 
			@RequestParam(name = "observacionGuardia") String observacionGuardia,
			RedirectAttributes atributos) {
				
		try {
			RetiroMaterial retiro = retiroServ.findById(idRetiro).orElseThrow();
			
			retiro.setFechaRetiro(new Date());
			
			if(retiro.getFechaRetiro().after(retiro.getFechaLimite())) {
				throw new Exception("Autorización caducada");
			}
			
			retiro.setObservacionGuardia(observacionGuardia);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			retiro.setUsuarioGuardia(usuarioServ.findByUsuario(auth.getName()));
			
			retiroServ.crearRetiroMaterial(retiro);
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		atributos.addFlashAttribute("success", "Retiro de material registrado con éxito!");
		return "redirect:/views/retiro-material";
	}
	
	@GetMapping("/previos")
	public String retirosPrevios(Model model,
						@RequestParam(name = "nroLegajo", required = false) Integer nroLegajo,
						@RequestParam(name = "idUsuario", required = false) Integer idUsuario,
						@RequestParam(name = "date_range") String date_range) throws ParseException {
				
		String[] parts = date_range.split("-");
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaInicioAux = formatter.parse(parts[0]);
		Date fechaFinalAux = new Date(formatter.parse(parts[1]).getTime() + (1000 * 60 * 60 * 24));
		
		List<RetiroMaterial> listaRetiros = retiroServ.findAllByOrderByFechaLimiteAsc();
		
		if(nroLegajo == -1) {		
			listaRetiros = listaRetiros.stream()
					.filter(a -> a.getFechaRetiro() != null
							&& a.getObservacionGuardia() != null
							&& a.getFechaRetiro().after(fechaInicioAux)
							&& a.getFechaRetiro().before(fechaFinalAux))
					.collect(Collectors.toList());
		}else if(nroLegajo == -2) {
			listaRetiros = listaRetiros.stream()
					.filter(a -> a.getFechaRetiro() != null
							&& a.getObservacionGuardia() != null
							&& a.getPersonal() == null
							&& a.getFechaRetiro().after(fechaInicioAux)
							&& a.getFechaRetiro().before(fechaFinalAux))
					.collect(Collectors.toList());
		}else {
			listaRetiros = listaRetiros.stream()
					.filter(a -> a.getFechaRetiro() != null
							&& a.getObservacionGuardia() != null
							&& a.getPersonal() != null
							&& a.getPersonal().getNroLegajo() == nroLegajo
							&& a.getFechaRetiro().after(fechaInicioAux)
							&& a.getFechaRetiro().before(fechaFinalAux))
					.collect(Collectors.toList());
		}
		
				
		if(idUsuario != -1) {
			listaRetiros = listaRetiros.stream()
					.filter(a -> a.getUsuarioSector().getIdUsuario() == idUsuario)
					.collect(Collectors.toList());
		}
		
	
		model.addAttribute("listaRetiros", listaRetiros);
		
		return "/views/retiro-material/verRetirosAnteriores";
	}
	
	@GetMapping("/autorizaciones-usuario")
	public String retirosPreviosUsuario(Model model) {
					
		List<RetiroMaterial> listaRetiros = retiroServ.findAllByOrderByFechaLimiteAsc();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		listaRetiros = listaRetiros.stream()
				.filter(a -> a.getFechaRetiro() != null
						&& a.getObservacionGuardia() != null
						&& a.getUsuarioSector().getUsuario().equals(auth.getName()))
				.collect(Collectors.toList());
			
	
		model.addAttribute("listaRetiros", listaRetiros);
		
		return "/views/retiro-material/verRetirosAnteriores";
	}
}