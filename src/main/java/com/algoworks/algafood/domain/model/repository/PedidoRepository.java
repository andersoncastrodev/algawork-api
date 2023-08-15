package com.algoworks.algafood.domain.model.repository;

import org.springframework.stereotype.Repository;

import com.algoworks.algafood.domain.model.Pedido;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> {

}
