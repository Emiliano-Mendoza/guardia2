package com.practicasupervisada.guardia2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.practicasupervisada.guardia2.domain.SectorTrabajo;
import com.practicasupervisada.guardia2.service.SectorTrabajoService;

@Controller
@RequestMapping("/views/sector_trabajo")
public class SectorTrabajoController {
	
	@Autowired
	private SectorTrabajoService sectorTrabajoServ;
		
	@GetMapping("/editar")
	public String interfazEditarSectorDeTrabajo(Model model) {
		
		List<SectorTrabajo> listaSectores = sectorTrabajoServ.getAllSectorTrabajo();
		
		model.addAttribute("listaSectores", listaSectores);
		
		return "/views/sector-trabajo/editarSector";
	}
	
	@PostMapping("/editar/{idSector}")
	public String editarSectorDeTrabajo(@PathVariable("idSector") int idSector,
										@RequestParam("sector") String sector,
										@RequestParam(name = "enabled", required = false) String enabled,
										Model model,
										RedirectAttributes atributos) {
		
		SectorTrabajo sectorTrabajo = sectorTrabajoServ.findById(idSector).get();
				
		if(sector == null || sector.length()==0) {
			
			List<SectorTrabajo> listaSectores = sectorTrabajoServ.getAllSectorTrabajo();
			
			model.addAttribute("listaSectores", listaSectores);
			model.addAttribute("error", "No se pudo editar el sector de trabajo");
			
			return "/views/sector-trabajo/editarSector";
			
		}
		
		try {
			sectorTrabajo.setSector(sector);
			
			if(enabled == null) {
				sectorTrabajo.setEnabled(false);
			}else if (enabled.equalsIgnoreCase("true")) {
				sectorTrabajo.setEnabled(true);
			}			
			
			sectorTrabajoServ.crearSectorTrabajo(sectorTrabajo);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		atributos.addFlashAttribute("success", "Sector de trabajo editado exitosamente!");
		
		
		return "redirect:/views/sector_trabajo/editar";
	}
	
	@PostMapping("/crear")
	public String nuevoSectorDeTrabajo(@RequestParam("sector") String sector,
										@RequestParam(name = "enabled", required = false) String enabled,
										Model model,
										RedirectAttributes atributos) {
		
		if(sector == null || sector.length()==0) {
			
			List<SectorTrabajo> listaSectores = sectorTrabajoServ.getAllSectorTrabajo();
			
			model.addAttribute("listaSectores", listaSectores);
			model.addAttribute("error", "No se pudo crear el sector de trabajo");
			
			return "/views/sector-trabajo/editarSector";
			
		}
		
		try {
			SectorTrabajo sectorTrabajo = new SectorTrabajo();			
			sectorTrabajo.setSector(sector);
			
			if(enabled == null) {
				sectorTrabajo.setEnabled(false);
			}else if (enabled.equalsIgnoreCase("true")) {
				sectorTrabajo.setEnabled(true);
			}			
			
			sectorTrabajo.setIdSector(sectorTrabajoServ.getAllSectorTrabajo().size()+1);
			
			sectorTrabajoServ.crearSectorTrabajo(sectorTrabajo);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		atributos.addFlashAttribute("success", "Sector de trabajo creado exitosamente!");
		
		return "redirect:/views/sector_trabajo/editar";
	}
}
