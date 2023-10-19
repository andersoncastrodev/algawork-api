package com.algoworks.algafood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.algoworks.algafood.infrastructere.repository.CustomJpaRepositoryImp;

//import springfox.documentation.oas.annotations.EnableOpenApi;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImp.class)
//@EnableWebMvc 
//@EnableSwagger2
//@EnableOpenApi
public class AlgaworkApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlgaworkApiApplication.class, args);
	}
 
}
