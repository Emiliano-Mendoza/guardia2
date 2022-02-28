package com.practicasupervisada.guardia2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.practicasupervisada.guardia2.service.impl.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth)
      throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bcrypt);
    }

	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/css/**", "/images/**", "/js/**", "/websocket").permitAll()
		.antMatchers("/views/personal").hasAnyRole("ADMIN","GUARDIA")
		.antMatchers("/views/retiro-material/autorizar").hasAnyRole("AUTORIZADOR", "ADMIN")
		.antMatchers("/views/retiro-material/autorizacion").hasAnyRole("AUTORIZADOR", "ADMIN")
		.antMatchers("/views/retiro-material/autorizaciones-usuario").hasAnyRole("AUTORIZADOR", "ADMIN")
		.antMatchers("/views/retiro-material").hasAnyRole("ADMIN","GUARDIA")
		.antMatchers("/views/retiro-material/retiro/**").hasAnyRole("GUARDIA")
		.antMatchers("/views/retiro-material/previos").hasAnyRole("GUARDIA", "ADMIN")
		.antMatchers("/views/personal/**").hasAnyRole("ADMIN")
		.antMatchers("/views/vehiculo/**").hasAnyRole("ADMIN")
		.antMatchers("/views/proveedor/**").hasAnyRole("ADMIN")
		.antMatchers("/views/evento").hasAnyRole("ADMIN","GUARDIA")
		.antMatchers("/views/evento/nuevo").hasAnyRole("NOTIFICADOR","ADMIN")
		.antMatchers("/views/evento/crear").hasAnyRole("NOTIFICADOR","ADMIN")
		.antMatchers("/views/evento/eventos-usuario").hasAnyRole("NOTIFICADOR","ADMIN")
		.antMatchers("/views/evento/ocurrencia/**").hasAnyRole("GUARDIA")
		.antMatchers("/views/evento/previos").hasAnyRole("GUARDIA","ADMIN")
		.antMatchers("/views/asistencia/ingreso-empleado/**").hasAnyRole("GUARDIA")
		.antMatchers("/views/asistencia/egreso-empleado/**").hasAnyRole("GUARDIA")
		.antMatchers("/views/asistencia/personal-ingresado").hasAnyRole("GUARDIA")
		.antMatchers("/views/asistencia/egreso-transitorio/**").hasAnyRole("GUARDIA")
		.antMatchers("/views/asistencia/reingreso-transitorio/**").hasAnyRole("GUARDIA")
		.antMatchers("/views/asistencia/personal/**").hasAnyRole("GUARDIA","ADMIN")
		.antMatchers("/views/asistencia/asistencias-empleado/**").hasAnyRole("GUARDIA","ADMIN")
		.antMatchers("/views/asistencia/previas").hasAnyRole("GUARDIA","ADMIN")
		.antMatchers("/views/asistencia/salidas-transitorias").hasAnyRole("GUARDIA","ADMIN")
		.antMatchers("/views/asistencia-proveedor/**").hasAnyRole("GUARDIA","ADMIN")		
		.antMatchers("/views/acontecimiento").hasAnyRole("GUARDIA","ADMIN")
		.antMatchers("/views/acontecimiento/guardar").hasAnyRole("GUARDIA")
		.antMatchers("/views/acontecimiento/previos").hasAnyRole("GUARDIA","ADMIN")
		.antMatchers("/views/usuario").hasAnyRole("ADMIN")
		.antMatchers("/views/usuario/**").hasAnyRole("ADMIN")
		.antMatchers("/views/sector_trabajo/**").hasAnyRole("ADMIN")
		.anyRequest().authenticated()
		.and()
		.formLogin().loginPage("/login").permitAll()
		.and()
		.logout().permitAll();
	}
}
