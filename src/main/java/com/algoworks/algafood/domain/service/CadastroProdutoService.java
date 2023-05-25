package com.algoworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algoworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algoworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algoworks.algafood.domain.model.Produto;
import com.algoworks.algafood.domain.model.Usuario;
import com.algoworks.algafood.domain.model.repository.ProdutoRepository;
import com.algoworks.algafood.domain.model.repository.RestauranteRepository;

@Service
public class CadastroProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Transactional
	public Produto salvar(Produto produto) {
		
		return produtoRepository.save(produto);
	}
	
	public Produto buscarOuFalhar(Long restauranteId, Long produtoId) {
		
		return produtoRepository.findById(restauranteId, produtoId)
				.orElseThrow( ()-> new ProdutoNaoEncontradoException(restauranteId, produtoId));
	}
	

}
