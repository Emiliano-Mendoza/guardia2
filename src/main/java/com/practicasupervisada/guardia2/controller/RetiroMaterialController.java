package com.practicasupervisada.guardia2.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
			
			model.addAttribute("retiro", listaRetiros);
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		
		//falta implementar
		return null;
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
}
