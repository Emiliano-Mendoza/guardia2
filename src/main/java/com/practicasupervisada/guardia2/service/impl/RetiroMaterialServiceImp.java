package com.practicasupervisada.guardia2.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practicasupervisada.guardia2.dao.MaterialRepo;
import com.practicasupervisada.guardia2.dao.RetiroMaterialRepo;
import com.practicasupervisada.guardia2.domain.RetiroMaterial;
import com.practicasupervisada.guardia2.service.RetiroMaterialService;

@Service
public class RetiroMaterialServiceImp implements RetiroMaterialService {
	
	@Autowired
	private RetiroMaterialRepo retiroRepo;
	@Autowired
	private MaterialRepo materialRepo;
	
	@Override
	public RetiroMaterial crearRetiroMaterial(RetiroMaterial retiro) {
		return retiroRepo.save(retiro);
	}

	@Override
	public List<RetiroMaterial> getAllRetiroMaterial() {
		return retiroRepo.findAll();
	}

	@Override
	public void eliminarRetiroMaterial(int idRetiroMaterial) {
		retiroRepo.deleteById(idRetiroMaterial);

	}

	@Override
	public Optional<RetiroMaterial> findById(int idRetiroMaterial) {
		return retiroRepo.findById(idRetiroMaterial);
	}

	@Override
	public RetiroMaterial crearRetiroMaterial(RetiroMaterial retiro, List<Integer> listaMateriales) {
		
		listaMateriales.forEach((mat)->{retiro.getMateriales().add(materialRepo.findById(mat).get());});
		
		return retiroRepo.save(retiro);
	}

	@Override
	public List<RetiroMaterial> findAllByOrderByFechaLimiteAsc() {
		return retiroRepo.findAllByOrderByFechaLimiteAsc();
	}

	@Override
	public List<RetiroMaterial> findAllByFechasRetiro(Date fecha1, Date fecha2) {
		return retiroRepo.findAllByFechasRetiro(fecha1, fecha2);
	}

	@Override
	public List<RetiroMaterial> findAllByFechasLimiteYRetiro(Date fecha1, Date fecha2) {
		return retiroRepo.findAllByFechasLimiteYRetiro(fecha1, fecha2);
	}

	@Override
	public List<RetiroMaterial> findAllByFechasRetiroDeRetiroExterno(Date fecha1, Date fecha2) {
		return retiroRepo.findAllByFechasRetiroDeRetiroExterno(fecha1, fecha2);
	}

	@Override
	public List<RetiroMaterial> findAllByFechasLimiteYRetiroDeRetiroExterno(Date fecha1, Date fecha2) {
		return retiroRepo.findAllByFechasLimiteYRetiroDeRetiroExterno(fecha1, fecha2);
	}

	@Override
	public List<RetiroMaterial> findAllByFechasRetiroDeEmpleado(Date fecha1, Date fecha2, Integer nroLegajo) {
		return retiroRepo.findAllByFechasRetiroDeEmpleado(fecha1, fecha2, nroLegajo);
	}

	@Override
	public List<RetiroMaterial> findAllByFechasLimiteYRetiroDeEmpleado(Date fecha1, Date fecha2, Integer nroLegajo) {
		return retiroRepo.findAllByFechasLimiteYRetiroDeEmpleado(fecha1, fecha2, nroLegajo);
	}

}
