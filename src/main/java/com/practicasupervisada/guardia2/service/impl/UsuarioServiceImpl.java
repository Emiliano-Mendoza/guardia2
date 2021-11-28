package com.practicasupervisada.guardia2.service.impl;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import com.practicasupervisada.guardia2.dao.UsuarioRepo;

import com.practicasupervisada.guardia2.domain.Usuario;
import com.practicasupervisada.guardia2.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioRepo usuarioRepo;

		
	@Override
	public Usuario crearUsuario(Usuario us) {
		
		String passCrip = new BCryptPasswordEncoder().encode(us.getContraseña()); 
		
		us.setContraseña(passCrip);
		us.setEnabled(true);
		
		return usuarioRepo.save(us);
	}

	@Override
	public List<Usuario> getAllUsuario() {		
		return usuarioRepo.findAll();
	}

	@Override
	public Optional<Usuario> findById(int idUsuario) {
		return usuarioRepo.findById(idUsuario);
	}

	@Override
	public Usuario findByUsuario(String us) {
		return usuarioRepo.findByUsuario(us);
	}



}
