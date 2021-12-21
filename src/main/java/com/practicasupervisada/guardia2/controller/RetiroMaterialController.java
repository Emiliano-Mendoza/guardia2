package com.practicasupervisada.guardia2.controller;

import java.text.SimpleDateFormat;
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

import com.practicasupervisada.guardia2.domain.Material;
import com.practicasupervisada.guardia2.domain.Personal;
import com.practicasupervisada.guardia2.domain.RetiroMaterial;
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
			
			//traigo solo aquellos retiros que no hayas sucedido y que la autorizacion aun este vigente
			listaRetiros = listaRetiros.stream()
					.filter(ret -> (ret.getFechaRetiro()==null && ret.getFechaLimite().after(new Date())))
					.collect(Collectors.toList());
			
			model.addAttribute("listaRetiros", listaRetiros);
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return "/views/retiro-material/retiro";
	}
	
	@GetMapping("/autorizar")
	public String listarClientesParaRetiroMaterial(Model model) {
		
		List<Personal> listaPersonal = personalServ.getAllPersonal();		
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
}