package com.algoworks.algafood;


import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CadastroCozinhaIntegrationTests {

	@LocalServerPort
	private int port;
	
	
	//Aqui sempre vai executar quando for iniciar os testes
	//Colocando parametros obrigatorios no inicio. 
	
	@BeforeEach
	public void setUp() {
		
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {
		RestAssured.given()
//				.basePath("/cozinhas")
//				.port(port)
				.accept(ContentType.JSON)
			.when()
				.get()
				.then()
				.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveConter4Cozinhas_QuandoConsultarCozinhas() {
		RestAssured.given()
//				.basePath("/cozinhas")
//				.port(port)
				.accept(ContentType.JSON)
			.when()
				.get()
				.then()
				.body("", Matchers.hasSize(4))
				.body("nome",Matchers.hasItems("Indiana","Tailandesa"));
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinha() {
		RestAssured.given()
			.body("{ \"nome\": \"Chinesa\"}")
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		  .when()
		  	.post()
		  .then()
		  	.statusCode(HttpStatus.CREATED.value());
	}
	
	
//	@Autowired
//	private CadastroCozinhaService cadastroCozinha;
//		
//	@Test
//	void CadastroCozinhaComSucesso() {
//		
//		//Cenario
//		Cozinha novaCozinha = new Cozinha();
//		
//		novaCozinha.setNome("Chinesa");
//			
//		//ação
//		novaCozinha = cadastroCozinha.salvar(novaCozinha);
//		
//		//validacão
//		assertThat(novaCozinha).isNotNull();
//		assertThat(novaCozinha.getId()).isNotNull();
//
//	}
//	
//	@Test
//	public void cadastroCozinhaSemNome() {
//		
//		Cozinha novaCozinha = new Cozinha();
//		   novaCozinha.setNome(null);
//		   
//		   ConstraintViolationException erroEsperado =
//		      Assertions.assertThrows(ConstraintViolationException.class, () -> {
//		         cadastroCozinha.salvar(novaCozinha);
//		      });
//		   
//		   assertThat(erroEsperado).isNotNull();
//	}
//	
//	@Test
//	public void deveFalhar_QuandoExcluirCozinhaEmUso() {
//		EntidadeEmUsoException erroEsperado =
//				Assertions.assertThrows(EntidadeEmUsoException.class, () -> {
//					cadastroCozinha.excluir(1L);
//				});
//
//		assertThat(erroEsperado).isNotNull();	
//	}
//	
//	@Test
//	public void deveFalhar_QuandoExcluirInexistente() {
//		CozinhaNaoEncontradaException erroEsperado =
//				Assertions.assertThrows(CozinhaNaoEncontradaException.class, () -> {
//					cadastroCozinha.excluir(100L);
//				});
//
//		assertThat(erroEsperado).isNotNull();
//	}
}
