package com.practicasupervisada.guardia2.service.impl;

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

}
