package com.practicasupervisada.guardia2.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practicasupervisada.guardia2.domain.Roles;

public interface RolesRepo extends JpaRepository<Roles, Integer> {
	
	public Roles findByRol(String rol);
	
}
