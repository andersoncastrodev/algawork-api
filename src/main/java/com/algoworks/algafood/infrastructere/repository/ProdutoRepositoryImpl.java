package com.algoworks.algafood.infrastructere.repository;

import org.springframework.stereotype.Repository;

import com.algoworks.algafood.domain.model.FotoProduto;
import com.algoworks.algafood.domain.repository.ProdutoRepositoryQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	@Override
	public FotoProduto save(FotoProduto foto) {
		return entityManager.merge(foto);
	}
	
	
}
