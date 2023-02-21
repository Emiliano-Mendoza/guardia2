package com.practicasupervisada.guardia2.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.practicasupervisada.guardia2.domain.Personal;
import com.practicasupervisada.guardia2.service.PersonalService;
import com.practicasupervisada.guardia2.util.LeerArchivo;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/views/escaner")
public class EscanerController {
	
	@Autowired
	private PersonalService personalServ;
	
	@GetMapping("/{fecha}")
	public ResponseEntity<List<Object>> obtenerInfo(@PathVariable("fecha") String fecha){
		
		fecha = fecha.replace("-", "/");
		
		Map<String,List<Object>> map = new HashMap<String,List<Object>>();
		
		File folder = new File("C:\\Personal");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				System.out.println("File " + listOfFiles[i].getName());

				try {
					File myObj = new File("C:\\Personal\\" + listOfFiles[i].getName());
					Scanner myReader = new Scanner(myObj);
					while (myReader.hasNextLine()) {
						String data = myReader.nextLine();
						
						String[] splited = data.split("\\s+");
				
						String fechaAux = splited[1] + " " + splited[2];
						
						Map<String,String> datos = new HashMap<String,String>();
						datos.put("fecha", fechaAux);
						datos.put("escaner", splited[3]);
						
						if(splited[1].equals(fecha)) {
						
							if(!map.containsKey(splited[0])) {															
								map.put(splited[0], new ArrayList<Object>());											
								map.get(splited[0]).add(new HashMap<String,Object>(datos));
							}else {
								if(!esRepetido(splited, map)) map.get(splited[0]).add(new HashMap<String,String>(datos));							
							}
						}
						datos.clear();	
																	
					}
					myReader.close();
					
				} catch (FileNotFoundException e) {
					System.out.println("An error occurred.");
					e.printStackTrace();
					return null;
				}			
				
			}
		}
		
		List<Object> list = new ArrayList<Object>();
				
		Map<String,Object> mapAux = new HashMap<String,Object>();

		map.forEach((k, v) -> {
			
			Optional<Personal> personal = personalServ.findById(Integer.parseInt(k));
			if(!personal.isEmpty()) {
				mapAux.put("empleado", personal.get());
			}else {
				mapAux.put("empleado", k);
			}
						
			mapAux.put("escaneos", v);
			list.add(new HashMap<String,Object>(mapAux));
			mapAux.clear();
		});
		System.out.println(list.toString());
		
		return ResponseEntity.ok(list);
	}
	
	private boolean esRepetido(String[] splited, Map<String,List<Object>> map) {
		
		Boolean resultado = false;
		String fechaAux = splited[1] + " " + splited[2];
		//Lo podria transformar a fecha y filtrar de ese modo los escaneos dobles con un minuto de diferencia
		//O simplemente comparo en el frontEnd

		try {
			resultado = map.get(splited[0]).stream().anyMatch(elemento -> ((Map<String, String>) elemento).get("fecha").equals(fechaAux));
		}
		catch(Exception e) {
			return false;
		}
	
		return resultado;
	}
	
}
