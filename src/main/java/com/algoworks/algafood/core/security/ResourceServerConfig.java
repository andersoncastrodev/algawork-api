package com.algoworks.algafood.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ch.qos.logback.core.pattern.color.BoldCyanCompositeConverter;

@Component
@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter { 
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 //OBS: Essa ORDEM é IMPORTANTE
		
		http.httpBasic() // TIRA A TELA DE LOGIN PADRÃO 
		
		//.and().formLogin(); AQUI COLOCA A TELA DE LOGIN NOVAMENTE.
		.and()
		
		.authorizeRequests() // AUTORIZA AS REQUISIÇÕES
		
			//.antMatchers("/cozinhas/**").permitAll() // LIBERANDO URL SENHA 
			// AUTENTICAÇÃO SEM ACESSO DE USUARIO
			// .antMatchers() IMPORTANTE 
 						
			.anyRequest().authenticated() //AQUI SOMENTE AS AUTORIZADAS
			.and()
			.cors() // HABILITANDO O CORS.
			.and()
			.oauth2ResourceServer() //2° PARTE ALTERADO PARA SER Oauth2 Resource
			
			.opaqueToken(); //Para Tratar os Tokens.
		

		 //CANCELAR A CRIAÇÃO DE COOKIES 
			//Usar o padrão STATELESS na API.
//		.and()
//		  .sessionManagement()
//		  .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//		  
//		  //DEBILITAR O Csrf()
//		  .and()
//		  	.csrf().disable();
		
		
	}

	// USUARIO EM MEMORIA TEM DE EXECUÇÃO
	// NÃO TRAZENDO DO BANCO DE DADOS.
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		
//		auth.inMemoryAuthentication()
//		
//		.withUser("thiago")
//			.password(passwordEncoder().encode("123"))
//			.roles("ADMIN")
//		.and()
//		.withUser("joao")
//		.password(passwordEncoder().encode("123"))
//		.roles("ADMIN");
//	}
//	
	
	
	// GERADOR DE SENHA FORTE
	// É Obrigatório 
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
	

}
