package com.practicasupervisada.guardia2.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practicasupervisada.guardia2.domain.Vehiculo;

public interface VehiculoRepo extends JpaRepository<Vehiculo, Integer> {

}
