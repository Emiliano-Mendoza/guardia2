package com.practicasupervisada.guardia2.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practicasupervisada.guardia2.domain.Usuario;

public interface UsuarioRepo extends JpaRepository<Usuario, Integer> {

	Usuario findByUsuario(String us);
	
}
