package com.practicasupervisada.guardia2.service;

import java.util.List;
import java.util.Optional;

import com.practicasupervisada.guardia2.domain.Material;

public interface MaterialService {
	
	public Material crearPersonal(Material m);
	public List<Material> getAllMaterial();
	public void eliminarMaterial(int idMaterial);
	public Optional<Material> findById(int idMaterial);
	
}
