package com.practicasupervisada.guardia2.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.practicasupervisada.guardia2.domain.Roles;
import com.practicasupervisada.guardia2.domain.Usuario;

public interface UsuarioService {
	
	public Usuario crearUsuario(Usuario us);
	public Usuario crearUsuario(Usuario us, Set<Roles> roles);
	public List<Usuario> getAllUsuario();
	public Optional<Usuario> findById(int idUsuario);
	public Usuario findByUsuario(String us);
	
}
