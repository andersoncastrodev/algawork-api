package com.algoworks.algafood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.algoworks.algafood.infrastructere.repository.CustomJpaRepositoryImp;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImp.class)
//@EnableWebMvc 
public class AlgaworkApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlgaworkApiApplication.class, args);
	}
 
}
