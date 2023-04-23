package com.practicasupervisada.guardia2.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;

import com.practicasupervisada.guardia2.service.PersonalService;

public class LeerArchivo {
	
	@Autowired
	private PersonalService personalServ;
	
	public List<Object> obtenerNombresArchivos() {
		
		Map<String,List<Object>> map = new HashMap<String,List<Object>>();
		
		File folder = new File("C:\\Guardia\\Personal");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				System.out.println("File " + listOfFiles[i].getName());

				try {
					File myObj = new File("C:\\Guardia\\Personal\\" + listOfFiles[i].getName());
					Scanner myReader = new Scanner(myObj);
					while (myReader.hasNextLine()) {
						String data = myReader.nextLine();
						
						String[] splited = data.split("\\s+");

						String aux = "Fecha: " + splited[1] + " " + splited[2] + " - E: " + splited[3];
						
						String fechaAux = splited[1] + " " + splited[2];
						
						Map<String,String> datos = new HashMap<String,String>();
						datos.put("fecha", fechaAux);
						datos.put("escaner", splited[3]);
						
						if(splited[1].equals("30/07/2022") ) {
						
							if(!map.containsKey(splited[0])) {															
								map.put(splited[0], new ArrayList<Object>());
								//map.put("empleado", personalServ.findById(1).get());					
								//map.get(splited[0]).add(aux);
								map.get(splited[0]).add(new HashMap<String,Object>(datos));
							}else {
								//map.get(splited[0]).add(aux);
								if(!esRepetido(splited, map)) map.get(splited[0]).add(new HashMap<String,String>(datos));							
							}
						}
						datos.clear();	
																	
					}
					myReader.close();
					
					//System.out.println(map.toString());
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
			mapAux.put("empleado", personalServ.findById(1).get());
			mapAux.put("escaneos", v);
			list.add(new HashMap<String,Object>(mapAux));
			mapAux.clear();
		});
		System.out.println(list.toString());
		
		return list;
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
