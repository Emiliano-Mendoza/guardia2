package com.practicasupervisada.guardia2.logs;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.practicasupervisada.guardia2.logs.LogAspect;

@Aspect
@Component
public class LogAspect {
	private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

	@Pointcut("execution(* com.practicasupervisada.guardia2.service.AcontecimientoService.crearAcontecimiento(..))")
	private void AcontecimientoServiceMetodos() {
	};

	@Pointcut("execution(* com.practicasupervisada.guardia2.service.AsistenciaService.crearAsistencia(..))")
	private void AsistenciaServiceMetodos() {
	};
	
	@Pointcut("execution(* com.practicasupervisada.guardia2.service.AsistenciaProveedorService.crearAsistencia(..))")
	private void AsistenciaProveedorServiceMetodos() {
	};

	@Pointcut("execution(* com.practicasupervisada.guardia2.service.EventoService.crearEvento(..))")
	private void EventoServiceMetodos() {
	};
	
	@Pointcut("execution(* com.practicasupervisada.guardia2.service.PersonalService.crearPersonal(..))")
	private void PersonalServiceMetodos() {
	};
	@Pointcut("execution(* com.practicasupervisada.guardia2.service.ProveedorService.crearProveedor(..))")
	private void ProveedorServiceMetodos() {
	};
	@Pointcut("execution(* com.practicasupervisada.guardia2.service.VehiculoService.crearVehiculo(..))")
	private void VehiculoServiceMetodos() {
	};

	@Pointcut("execution(* com.practicasupervisada.guardia2.service.RetiroMaterialService.crearRetiroMaterial(..))")
	private void RetiroMaterialServiceMetodos() {
	};
	
	@Pointcut("execution(* com.practicasupervisada.guardia2.service.TransitoService.crearTransito(..))")
	private void TransitoServiceMetodos() {
	};
	
	@Pointcut("execution(* com.practicasupervisada.guardia2.service.UsuarioService.crearUsuario(..))")
	private void UsuarioServiceMetodos() {
	};
	

	@Before("AcontecimientoServiceMetodos() || AsistenciaServiceMetodos() ||"
			+ " EventoServiceMetodos() || RetiroMaterialServiceMetodos() || AsistenciaProveedorServiceMetodos() ||"
			+ "PersonalServiceMetodos() || ProveedorServiceMetodos() || VehiculoServiceMetodos() || "
			+ "TransitoServiceMetodos() || UsuarioServiceMetodos()")
	public void hacerAntes(JoinPoint jp) {

		// TRACE -> DEBUG -> INFO -> WARN -> ERROR -> FATAL
		logger.info("A continuacion se ejecutara el metodo: " + jp.getSignature().getName());
		logger.info("Argumentos: " + Arrays.toString(jp.getArgs()));
	}
	

	@After("AcontecimientoServiceMetodos() || AsistenciaServiceMetodos() ||"
			+ " EventoServiceMetodos() || RetiroMaterialServiceMetodos() || AsistenciaProveedorServiceMetodos() ||"
			+ "PersonalServiceMetodos() || ProveedorServiceMetodos() || VehiculoServiceMetodos() || "
			+ "TransitoServiceMetodos() || UsuarioServiceMetodos()")
	public void hacerDespues(JoinPoint jp) {
		logger.info("Se ha ejecutado el metodo: " + jp.getSignature().getName());
	}

}
