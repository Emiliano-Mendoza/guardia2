package com.practicasupervisada.guardia2.logs;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
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
	public static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

	@Pointcut("execution(* com.practicasupervisada.guardia2.service.AcontecimientoService.crearAcontecimiento(..))")
	private void AcontecimientoServiceMetodos() {
	};

	@Pointcut("execution(* com.practicasupervisada.guardia2.service.AsistenciaService.crearAsistencia(..)) || execution(* com.practicasupervisada.guardia2.service.AsistenciaService.actualizarAsistencia(..))")
	private void AsistenciaServiceMetodos() {
	};

	@Pointcut("execution(* com.practicasupervisada.guardia2.service.AsistenciaProveedorService.crearIngresoProveedor(..)) || execution(* com.practicasupervisada.guardia2.service.AsistenciaProveedorService.registrarEgresoProveedor(..))")
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

	@Pointcut("execution(* com.practicasupervisada.guardia2.service.TransitoService.crearTransito(..)) || execution(* com.practicasupervisada.guardia2.service.TransitoService.actualizarTransito(..)) ")
	private void TransitoServiceMetodos() {
	};

	@Pointcut("execution(* com.practicasupervisada.guardia2.service.UsuarioService.crearUsuario(..))")
	private void UsuarioServiceMetodos() {
	};
	
	@Pointcut("execution(* com.practicasupervisada.guardia2.service.SectorTrabajoService.crearSectorTrabajo(..))")
	private void SectorTrabajoServiceMetodos() {
	};
	
	@Pointcut("execution(* com.practicasupervisada.guardia2.service.TransitoService.inspecciondarTransitosExpirados(..))")
	private void expirarTransitoMetodo() {
	};

	@Before("AcontecimientoServiceMetodos() || AsistenciaServiceMetodos() ||"
			+ " EventoServiceMetodos() || RetiroMaterialServiceMetodos() || AsistenciaProveedorServiceMetodos() ||"
			+ "PersonalServiceMetodos() || ProveedorServiceMetodos() || VehiculoServiceMetodos() || "
			+ "TransitoServiceMetodos() || UsuarioServiceMetodos() || SectorTrabajoServiceMetodos()")
	public void hacerAntes(JoinPoint jp) {

		// TRACE -> DEBUG -> INFO -> WARN -> ERROR -> FATAL
		logger.info("###### A continuacion se ejecutara el metodo: " + jp.getSignature().getName());
		logger.info("###### Argumentos: " + Arrays.toString(jp.getArgs()));
	}

	@AfterReturning(pointcut = "AcontecimientoServiceMetodos() || AsistenciaServiceMetodos() || EventoServiceMetodos() || RetiroMaterialServiceMetodos() || AsistenciaProveedorServiceMetodos() ||PersonalServiceMetodos() || ProveedorServiceMetodos() || VehiculoServiceMetodos() || TransitoServiceMetodos() || UsuarioServiceMetodos() || SectorTrabajoServiceMetodos() ", returning = "result")
	public void audit(JoinPoint joinPoint, Object result) throws Throwable {
		
		logger.info("&&&&&& Retorno de la clase : {} ; Metodo : {} ", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName());
	    if (result != null) {
	        logger.info("&&&&&& Valor de retorno : {}", result.toString());
	    } else{
	        logger.info("&&&&&& Valor de retorno nulo.");
	    }
		
	    
	}
	
	
//	@Before("expirarTransitoMetodo()")
//	public void hacerAntesExpiracion(JoinPoint jp) {
//		
//		logger.info("@@@@@@ Inspeccion de Transitos expirados ; Metodo : " + jp.getSignature().getName());
//		
//	}
//	@After("expirarTransitoMetodo()")
//	public void hacerDespuesExpiracion(JoinPoint jp) {
//		
//		logger.info("@@@@@@ Finalizacion de inspeccion ; Metodo : " + jp.getSignature().getName());
//		
//	}
}
