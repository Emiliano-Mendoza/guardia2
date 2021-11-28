package com.practicasupervisada.guardia2.controller;

import java.text.SimpleDateFormat;
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

import com.practicasupervisada.guardia2.domain.Personal;
import com.practicasupervisada.guardia2.domain.RetiroMaterial;
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
	
	@PostMapping("/autorizacion")
	public String nuevaAutorizacionDeRetiro(@RequestParam(name = "desc") String desc,
											@RequestParam(name = "fechaLimite") String fecha,
											@RequestParam(name = "nroLegajo") int nroLegajo,
											RedirectAttributes atributos){
		
		//Le doy el formato correcto a la fecha obtenida
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			Personal empleado = personalServ.findById(nroLegajo).orElseThrow();
			RetiroMaterial retiro = new RetiroMaterial();
			
			retiro.setDescripcion(desc);
			
			Date fechaLimite = formatter.parse(fecha + " 23:59:59");
			retiro.setFechaLimite(fechaLimite);
			
			retiro.setPersonal(empleado);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			retiro.setUsuarioSector(usuarioServ.findByUsuario(auth.getName()));
			
			retiroServ.crearRetiroMaterial(retiro);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		atributos.addFlashAttribute("success", "Autorización creada exitosamente!");
		return "redirect:/views/personal/autorizar-retiro";
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