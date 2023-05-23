package com.algoworks.algafood.domain.model.repository;

import org.springframework.stereotype.Repository;
import com.algoworks.algafood.domain.model.Produto;

@Repository
public interface ProdutoRepository extends CustomJpaRepository<Produto, Long>  {

	
}
