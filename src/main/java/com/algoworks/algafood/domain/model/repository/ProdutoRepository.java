package com.algoworks.algafood.domain.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.algoworks.algafood.domain.model.Produto;
import com.algoworks.algafood.domain.model.Restaurante;

@Repository
public interface ProdutoRepository extends CustomJpaRepository<Produto, Long>  {

	//Pegar fazer o metodo buscarOuFalhar
	@Query("from Produto where restaurante.id = :restaurante and id = :produto")
	Optional<Produto> findById(@Param("restaurante") Long restauranteId, @Param("produto") Long produtoId );
	
	public List<Produto> findByRestaurante(Restaurante restaurante);
}
