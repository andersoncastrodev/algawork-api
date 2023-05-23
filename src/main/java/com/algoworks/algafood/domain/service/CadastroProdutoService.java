package com.algoworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algoworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algoworks.algafood.domain.model.Usuario;
import com.algoworks.algafood.domain.model.repository.ProdutoRepository;
import com.algoworks.algafood.domain.model.repository.RestauranteRepository;

@Service
public class CadastroProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private RestauranteRepository restauranteRepository;

	
	

}
