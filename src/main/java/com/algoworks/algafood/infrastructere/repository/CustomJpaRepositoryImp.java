package com.algoworks.algafood.infrastructere.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.algoworks.algafood.domain.model.repository.CustomJpaRepository;

import jakarta.persistence.EntityManager;

public class CustomJpaRepositoryImp<T, ID> extends SimpleJpaRepository<T, ID> implements CustomJpaRepository<T, ID>{

	private EntityManager entityManager;
	
	//Construtor que foi Subcrito.
	//Tudo isso é para ter um forma de consultas personalizadas usando o EntityManager do JPA (padrao)
	public CustomJpaRepositoryImp(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		
		this.entityManager = entityManager;
	}

	//Nosso Metodo 
	@Override
	public Optional<T> buscarPrimeiro() {
		
		//Montando a consulta dinanica com jpql
		var jpql = "from "+ getDomainClass().getName();
		
		//Mesmo sql com o limit 1 para trazer apenas um registro
		T entity = entityManager.createQuery(jpql, getDomainClass()).setMaxResults(1).getSingleResult();
		
		//Agora retorna o possivel objeto.
		return Optional.ofNullable(entity);
	}

}
