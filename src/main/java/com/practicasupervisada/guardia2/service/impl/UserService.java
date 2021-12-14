package com.practicasupervisada.guardia2.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.practicasupervisada.guardia2.dao.UsuarioRepo;
import com.practicasupervisada.guardia2.domain.Usuario;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepo usuarioRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario us = usuarioRepo.findByUsuario(username);
		
		if(us==null) {
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
		if(!us.getEnabled()) {
			throw new UsernameNotFoundException("Usuario deshabilitado");
		}
		
		List<GrantedAuthority> roles = new ArrayList<>();
		
		us.getRoles().forEach(rol -> {roles.add(new SimpleGrantedAuthority("ROLE_" + rol.getRol()));});
		
		UserDetails userDet = new User(us.getUsuario(), us.getContraseña(), roles);
		
		return userDet;
	}

}
