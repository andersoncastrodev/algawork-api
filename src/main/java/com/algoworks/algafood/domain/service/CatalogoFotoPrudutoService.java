package com.algoworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algoworks.algafood.domain.model.FotoProduto;
import com.algoworks.algafood.domain.repository.ProdutoRepository;

import jakarta.transaction.Transactional;

@Service
public class CatalogoFotoPrudutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Transactional
	public FotoProduto salvar(FotoProduto foto) {
		return produtoRepository.save(foto);
	}
}
