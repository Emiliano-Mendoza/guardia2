package com.practicasupervisada.guardia2.service;

import java.util.List;
import java.util.Optional;

import com.practicasupervisada.guardia2.domain.Usuario;

public interface UsuarioService {
	
	public Usuario crearUsuario(Usuario us);
	public Usuario crearUsuario(Usuario us, List<String> roles);
	public Usuario editarUsuario(Usuario us, List<String> roles);
	public List<Usuario> getAllUsuario();
	public Optional<Usuario> findById(int idUsuario);
	public Usuario findByUsuario(String us);
	
}
