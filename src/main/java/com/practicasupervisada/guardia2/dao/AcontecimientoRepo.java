package com.practicasupervisada.guardia2.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practicasupervisada.guardia2.domain.Acontecimiento;

public interface AcontecimientoRepo extends JpaRepository<Acontecimiento, Integer> {

}
