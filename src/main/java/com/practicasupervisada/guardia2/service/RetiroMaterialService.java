package com.practicasupervisada.guardia2.service;

import java.util.List;
import java.util.Optional;

import com.practicasupervisada.guardia2.domain.RetiroMaterial;

public interface RetiroMaterialService {
	
	public RetiroMaterial crearRetiroMaterial(RetiroMaterial retiro);
	public List<RetiroMaterial> getAllRetiroMaterial();
	public void eliminarRetiroMaterial(int idRetiroMaterial);
	public Optional<RetiroMaterial> findById(int idRetiroMaterial);
	
}
