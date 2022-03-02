package com.practicasupervisada.guardia2.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.practicasupervisada.guardia2.domain.RetiroMaterial;

public interface RetiroMaterialService {

	public RetiroMaterial crearRetiroMaterial(RetiroMaterial retiro);
	public RetiroMaterial crearRetiroMaterial(RetiroMaterial retiro, List<Integer> listaMateriales);
	public List<RetiroMaterial> getAllRetiroMaterial();
	public void eliminarRetiroMaterial(int idRetiroMaterial);
	public Optional<RetiroMaterial> findById(int idRetiroMaterial);
	
	public List<RetiroMaterial> findAllByOrderByFechaLimiteAsc();
	
	public List<RetiroMaterial> findAllByFechasRetiro(Date fecha1, Date fecha2);
	public List<RetiroMaterial> findAllByFechasLimiteYRetiro(Date fecha1, Date fecha2);
	
	public List<RetiroMaterial> findAllByFechasRetiroDeRetiroExterno(Date fecha1, Date fecha2);
	public List<RetiroMaterial> findAllByFechasLimiteYRetiroDeRetiroExterno(Date fecha1, Date fecha2);
	
	public List<RetiroMaterial> findAllByFechasRetiroDeEmpleado(Date fecha1, Date fecha2, Integer nroLegajo);
	public List<RetiroMaterial> findAllByFechasLimiteYRetiroDeEmpleado(Date fecha1, Date fecha2, Integer nroLegajo);
}
