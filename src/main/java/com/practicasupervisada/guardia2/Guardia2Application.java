package com.practicasupervisada.guardia2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/* Cosas por hacer:
 * 
 * --------Visualizar los acontecimientos por guardia que los hizo (agregar a la tabla)
 * --------Todos los guardias pueden ver el calendario de acontecimientos
 * --------Ordenamiento por columna
 * --------Editar personal + agregar foto
 * --------Editar evento
 * --------En el evento se debe figurar a quien debe avisar la guardia en el caso de ocurrencia
 * --------Agregar atributo de cancelación de evento + descripcion del motivo
 * --------Agregar el Modulo de Transito + Vehiculo 
 * --------Agregar lista desplegable de materiales que va a retirar un empleado
 * --------Inhabilitar usuario
 * --------Notificaciones X
 * Redirigir a la pagina de inicio luego de iniciar sesion
*/

@SpringBootApplication
public class Guardia2Application {

	public static void main(String[] args) {
		SpringApplication.run(Guardia2Application.class, args);
	}

}
