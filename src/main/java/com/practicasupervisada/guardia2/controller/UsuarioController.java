package com.practicasupervisada.guardia2.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.practicasupervisada.guardia2.dao.RolesRepo;
import com.practicasupervisada.guardia2.domain.Roles;
import com.practicasupervisada.guardia2.domain.Usuario;
import com.practicasupervisada.guardia2.service.UsuarioService;

@Controller
@RequestMapping("/views/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioServ;
	@Autowired
	private RolesRepo rolesRepo;
	
	@GetMapping
	public String listarUsuarios(Model model) {
		
		List<Usuario> listaUsuarios = usuarioServ.getAllUsuario();
		
		model.addAttribute("listaUsuarios", listaUsuarios);
		
		return null;
	}
	
	@GetMapping("/crear")
	public String crearNuevoUsuario(Model model) {
		
		Usuario usuario = new Usuario();
		
		model.addAttribute("usuario", usuario);
		
		return "/views/usuario/nuevoUsuario";
	}
	
	@PostMapping("/guardar")
	public String guardar(@RequestParam(name = "usuario") String usuario,
						  @RequestParam(name = "contraseña") String contraseña,
						  @RequestParam(name = "rol") List<String> roles,
						  RedirectAttributes atributos,
						  Model model) {
		
		//arreglar
		System.out.println(roles);
		
		if(usuario!=null && contraseña!=null && !roles.isEmpty() &&
				usuarioServ.findByUsuario(usuario)==null) {
			Usuario usuarioNuevo = new Usuario();
			usuarioNuevo.setContraseña(contraseña);
			usuarioNuevo.setUsuario(usuario);
			//usuarioNuevo.setRol(roles.get(0));
			
			roles.forEach((rol)->{usuarioNuevo.getRoles().add(rolesRepo.findByRol(rol));});
			
			//usuarioNuevo.getRoles().add(rolesRepo.findByRol(rol));
			
			try {
				usuarioServ.crearUsuario(usuarioNuevo);
				
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}else {
			
			model.addAttribute("error", "No se pudo crear el nuevo usuario");
			return "/views/usuario/nuevoUsuario";
		}
		
		/*
		if(result.hasErrors()) {
			
			model.addAttribute("usuario", usuario);
			System.out.println("Formulario incorrecto");
			System.out.println(result.toString());
			
			return "/views/usuario/nuevoUsuario";
		}
		
		try {
			usuarioServ.crearUsuario(usuario);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		} */
		
		atributos.addFlashAttribute("success", "Usuario creado exitosamente!");
		return "redirect:/views/usuario/crear";
	}
}
