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

import com.practicasupervisada.guardia2.domain.Usuario;
import com.practicasupervisada.guardia2.service.UsuarioService;

@Controller
@RequestMapping("/views/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioServ;

	
	@GetMapping("/editar")
	public String listarUsuarios(Model model) {
				
		List<Usuario> listaUsuarios = usuarioServ.getAllUsuario();
		
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		Usuario usuarioActual = usuarioServ.findByUsuario(auth.getName());
//		
//		//saco de la lista al usuario actual
//		listaUsuarios = listaUsuarios.stream()
//				.filter(us -> us.getIdUsuario() != usuarioActual.getIdUsuario())
//				.collect(Collectors.toList());
		
		model.addAttribute("listaUsuarios", listaUsuarios);
		model.addAttribute("cantHoras", AsistenciaController.cantHoras);
		
		return "/views/usuario/editarUsuario";
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
						  @RequestParam(name = "nombre") String nombre,
						  @RequestParam(name = "apellido") String apellido,
						  @RequestParam(name = "rol") List<String> roles,
						  RedirectAttributes atributos,
						  Model model) {
		
		//verifico si ya existe el username
		if(usuarioServ.findByUsuario(usuario)!=null) {

			model.addAttribute("error", "Usuario existente. No se pudo crear el nuevo usuario.");
			return "/views/usuario/nuevoUsuario";
		}
		
		if(usuario!=null && contraseña!=null && !roles.isEmpty() && nombre!=null && apellido!=null
				&& usuario.length()>0 && contraseña.length()>5 && nombre.length()>0 && apellido.length()>0) {
			
			Usuario usuarioNuevo = new Usuario();
			usuarioNuevo.setContraseña(contraseña);
			usuarioNuevo.setUsuario(usuario);
			usuarioNuevo.setNombre(nombre);
			usuarioNuevo.setApellido(apellido);
			usuarioNuevo.setEnabled(true);
			
			try {
				usuarioServ.crearUsuario(usuarioNuevo, roles);
				
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}else {
			
			model.addAttribute("error", "Datos incompletos. No se pudo crear el nuevo usuario");
			return "/views/usuario/nuevoUsuario";
		}
		
		
		atributos.addFlashAttribute("success", "Usuario creado exitosamente!");
		return "redirect:/views/usuario/crear";
	}
	
	@PostMapping("/editar/{idUsuario}")
	public String editarUsuario(@PathVariable("idUsuario") int idUsuario,
								@RequestParam(name = "nombre") String nombre,
								@RequestParam(name = "apellido") String apellido,
								@RequestParam(name = "contraseña", required = false) String contraseña,
								@RequestParam(name = "rol") List<String> roles,
								@RequestParam(name = "enabled", required = false) String enabled,
								RedirectAttributes atributos,
								Model model) {
				
		try {
			Usuario user = usuarioServ.findById(idUsuario).orElseThrow();
			user.setNombre(nombre);
			user.setApellido(apellido);
			
			if(enabled == null) {
				user.setEnabled(false);
			}else if (enabled.equalsIgnoreCase("true")) {
				user.setEnabled(true);
			}
			
			user.getRoles().clear();
			
			if(contraseña.equals("") || contraseña == null) {
				
				usuarioServ.editarUsuario(user, roles);
				
			}else {
				
				user.setContraseña(contraseña);
				usuarioServ.crearUsuario(user, roles);
			}
								
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		atributos.addFlashAttribute("success", "Usuario editado exitosamente!");
		return "redirect:/views/usuario/editar";
	}
}
