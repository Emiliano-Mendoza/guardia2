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
		http.csrf().disable().authorizeRequests().antMatchers("/index", "/home", "/", "/css/**", "/images/**", "/js/**").permitAll()
		.antMatchers("/views/personal").hasAnyRole("USER","ADMIN","GUARDIA")
		.antMatchers("/views/personal/autorizar-retiro").hasAnyRole("AUTORIZADOR", "ADMIN")
		.antMatchers("/views/retiro-material/autorizacion").hasAnyRole("AUTORIZADOR", "ADMIN")
		.antMatchers("/views/retiro-material").hasAnyRole("USER","ADMIN","GUARDIA")
		.antMatchers("/views/retiro-material/retiro/**").hasAnyRole("USER","GUARDIA")
		.antMatchers("/views/personal/agregar").hasAnyRole("ADMIN")
		.antMatchers("/views/personal/guardar").hasAnyRole("ADMIN")
		.antMatchers("/views/evento").hasAnyRole("USER","ADMIN","GUARDIA")
		.antMatchers("/views/evento/nuevo").hasAnyRole("NOTIFICADOR","ADMIN")
		.antMatchers("/views/evento/crear").hasAnyRole("NOTIFICADOR","ADMIN")
		.antMatchers("/views/evento/ocurrencia/**").hasAnyRole("USER","GUARDIA")
		.antMatchers("/views/asistencia/ingreso-empleado/**").hasAnyRole("USER","GUARDIA")
		.antMatchers("/views/asistencia/egreso-empleado/**").hasAnyRole("USER","GUARDIA")
		.antMatchers("/views/asistencia/personal-ingresado").hasAnyRole("USER","GUARDIA")
		.antMatchers("/views/acontecimiento").hasAnyRole("USER","GUARDIA")
		.antMatchers("/views/acontecimiento/guardar").hasAnyRole("USER","GUARDIA")
		.antMatchers("/views/usuario").hasAnyRole("ADMIN")
		.antMatchers("/views/usuario/crear").hasAnyRole("ADMIN")
		.antMatchers("/views/usuario/guardar").hasAnyRole("ADMIN")
		.anyRequest().authenticated()
		.and()
		.formLogin().permitAll()
		.and()
		.logout().permitAll();
	}
}
