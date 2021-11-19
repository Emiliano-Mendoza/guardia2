package com.practicasupervisada.guardia2.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.practicasupervisada.guardia2.domain.Personal;
import com.practicasupervisada.guardia2.domain.RetiroMaterial;
import com.practicasupervisada.guardia2.service.PersonalService;
import com.practicasupervisada.guardia2.service.RetiroMaterialService;

@Controller
@RequestMapping("/views/retiro-material")
public class RetiroMaterialController {
	
	@Autowired
	private RetiroMaterialService retiroServ;
	@Autowired
	private PersonalService personalServ;
	
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
		
		//falta implementar
		return "/views/retiro-material/retiro";
	}
	
	@PostMapping("/autorizacion")
	public String nuevaAutorizacionDeRetiro(@RequestParam(name = "desc") String desc,
											@RequestParam(name = "fechaLimite") String fecha,
											@RequestParam(name = "nroLegajo") int nroLegajo){
		
		//Le doy el formato correcto a la fecha obtenida
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			Personal empleado = personalServ.findById(nroLegajo).orElseThrow();
			RetiroMaterial retiro = new RetiroMaterial();
			
			retiro.setDescripcion(desc);
			
			Date fechaLimite = formatter.parse(fecha + " 23:59:59");
			retiro.setFechaLimite(fechaLimite);
			
			retiro.setPersonal(empleado);
			
			retiroServ.crearRetiroMaterial(retiro);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return "redirect:/views/personal/autorizar-retiro";
	}
	
	@PostMapping("/retiro/{idRetiro}")
	public String egreso(@PathVariable("idRetiro") int idRetiro, 
			@RequestParam(name = "observacionGuardia") String observacionGuardia) {
				
		try {
			RetiroMaterial retiro = retiroServ.findById(idRetiro).orElseThrow();
			
			retiro.setFechaRetiro(new Date());
			
			if(retiro.getFechaRetiro().after(retiro.getFechaLimite())) {
				throw new Exception("Autorizacion caducada");
			}
			
			retiro.setObservacionGuardia(observacionGuardia);
			
			retiroServ.crearRetiroMaterial(retiro);
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return "redirect:/views/retiro-material";
	}
}
