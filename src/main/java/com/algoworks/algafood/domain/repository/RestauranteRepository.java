package com.algoworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.algoworks.algafood.domain.model.Restaurante;

@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries {

	List<Restaurante> queryByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal );
	
	
	@Query("from Restaurante where nome like %:nome% and cozinha.id = :cozinha")
	List<Restaurante> consultarPorNome(String nome, Long cozinha);
	
	// OU - Usando o @Param("nome")
	//@Query("from Restaurante where nome like %:consulta% and cozinha.id = :id")
	//List<Restaurante> consultarPorNome(@Param("consulta") String nome, @Param("id") Long cozinha );

	//@Query("from Resturante where nome like %:consulta% and cozinha.id = :id")
//	List<Restaurante> consultarPorNome(@Param("consulta")String nome, @Param("id") Long cozinha );
	
	
	//Faz un Like e (AND) Cozinha Id. (Cozinha codigo)
	List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinha);
	
	//Usando o Optional será esperado apenas 1 restaurante.
	//Aqui traz o primeiro registro
	Optional<Restaurante> findFirstResturanteByNomeContaining(String nome);
	
	//Aqui traz o 2 primeiro Top 2
	List<Restaurante> findTop2ByNomeContaining(String nome);
	
	
	int countByCozinhaId(Long cozinha);
	
	//Implementação Customizada Da Classe RestauranteRepositoryImpl - Obs a classe deve ter o Impl no final da classe
	// e deve ser o mesmo nome do Repository
	//  List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
}
