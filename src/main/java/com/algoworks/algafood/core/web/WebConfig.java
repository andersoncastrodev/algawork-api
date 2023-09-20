package com.algoworks.algafood.core.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	//Habilitando o CORS Global na Aplicação inteira.
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		
		//Habilita para qualquer EndPonit da API.
		registry.addMapping("/**")
		
		//Permitir qualquer origem.
		.allowedOrigins("*");
			
	}
}
