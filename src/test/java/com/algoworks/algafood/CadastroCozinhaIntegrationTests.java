package com.algoworks.algafood;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.algoworks.algafood.domain.model.Cozinha;
import com.algoworks.algafood.domain.service.CadastroCozinhaService;

import jakarta.validation.ConstraintViolationException;

@SpringBootTest
class CadastroCozinhaIntegrationTests {

	@Autowired
	private CadastroCozinhaService cadastroCozinha;
		
//	@Test
//	void contextLoads() {
//		assertFalse(true);
//		
//		assertFalse(false);
//	}
	
	@Test
	void testarCadastroCozinhaComSucesso() {
		//Cenario
		Cozinha novaCozinha = new Cozinha();
		
		novaCozinha.setNome("Chinesa");
	
		
		//ação
		novaCozinha = cadastroCozinha.salvar(novaCozinha);
		
		//validacão
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
		
	
	}
	
	@Test
	public void deveFalhaAoCadastrarCozinha_QuandoSemNome() {
		
		Cozinha novaCozinha = new Cozinha();
		   novaCozinha.setNome(null);
		   
		   ConstraintViolationException erroEsperado =
		      Assertions.assertThrows(ConstraintViolationException.class, () -> {
		         cadastroCozinha.salvar(novaCozinha);
		      });
		   
		   assertThat(erroEsperado).isNotNull();
	}
	
}
