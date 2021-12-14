package com.practicasupervisada.guardia2.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practicasupervisada.guardia2.dao.MaterialRepo;
import com.practicasupervisada.guardia2.domain.Material;
import com.practicasupervisada.guardia2.service.MaterialService;

@Service
public class MaterialServiceImp implements MaterialService {
	
	@Autowired
	private MaterialRepo materialRepo;
	
	@Override
	public Material crearPersonal(Material m) {		
		return materialRepo.save(m);
	}

	@Override
	public List<Material> getAllMaterial() {
		return materialRepo.findAll();
	}

	@Override
	public void eliminarMaterial(int idMaterial) {
		materialRepo.deleteById(idMaterial);
	}

	@Override
	public Optional<Material> findById(int idMaterial) {
		return materialRepo.findById(idMaterial);
	}


}
