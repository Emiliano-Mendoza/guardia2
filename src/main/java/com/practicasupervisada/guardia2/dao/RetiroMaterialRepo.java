package com.practicasupervisada.guardia2.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.practicasupervisada.guardia2.domain.RetiroMaterial;

public interface RetiroMaterialRepo extends JpaRepository<RetiroMaterial, Integer> {
	
	public List<RetiroMaterial> findAllByOrderByFechaLimiteAsc();
	
	
	@Query(
			value = "SELECT * FROM retiro_material r WHERE r.fecha_retiro >= :fecha1 AND r.fecha_retiro <= :fecha2 ORDER BY fecha_limite", 
			nativeQuery = true)
	public List<RetiroMaterial> findAllByFechasRetiro(
			@Param("fecha1") Date fecha1, @Param("fecha2") Date fecha2);
	
	@Query(
			value = "SELECT * FROM retiro_material r WHERE (r.fecha_retiro >= :fecha1 AND r.fecha_retiro <= :fecha2) OR (r.fecha_limite >= :fecha1 AND r.fecha_limite <= :fecha2) ORDER BY fecha_limite", 
			nativeQuery = true)
	public List<RetiroMaterial> findAllByFechasLimiteYRetiro(
			@Param("fecha1") Date fecha1, @Param("fecha2") Date fecha2);
	
	//----
	
	@Query(
			value = "SELECT * FROM retiro_material r WHERE r.fecha_retiro >= :fecha1 AND r.fecha_retiro <= :fecha2 AND r.id_personal IS NULL ORDER BY fecha_limite", 
			nativeQuery = true)
	public List<RetiroMaterial> findAllByFechasRetiroDeRetiroExterno(
			@Param("fecha1") Date fecha1, @Param("fecha2") Date fecha2);
	
	@Query(
			value = "SELECT * FROM retiro_material r WHERE ((r.fecha_retiro >= :fecha1 AND r.fecha_retiro <= :fecha2) OR (r.fecha_limite >= :fecha1 AND r.fecha_limite <= :fecha2)) AND r.id_personal IS NULL ORDER BY fecha_limite", 			
			nativeQuery = true)
	public List<RetiroMaterial> findAllByFechasLimiteYRetiroDeRetiroExterno(
			@Param("fecha1") Date fecha1, @Param("fecha2") Date fecha2);
	
	//----
	
	@Query(
			value = "SELECT * FROM retiro_material r WHERE r.fecha_retiro >= :fecha1 AND r.fecha_retiro <= :fecha2 AND r.id_personal = :nroLegajo ORDER BY fecha_limite", 
			nativeQuery = true)
	public List<RetiroMaterial> findAllByFechasRetiroDeEmpleado(
			@Param("fecha1") Date fecha1, @Param("fecha2") Date fecha2, @Param("nroLegajo") Integer nroLegajo);
	
	@Query(
			value = "SELECT * FROM retiro_material r WHERE ((r.fecha_retiro >= :fecha1 AND r.fecha_retiro <= :fecha2) OR (r.fecha_limite >= :fecha1 AND r.fecha_limite <= :fecha2)) AND r.id_personal = :nroLegajo ORDER BY fecha_limite", 
			nativeQuery = true)
	public List<RetiroMaterial> findAllByFechasLimiteYRetiroDeEmpleado(
			@Param("fecha1") Date fecha1, @Param("fecha2") Date fecha2, @Param("nroLegajo") Integer nroLegajo);
	
}
