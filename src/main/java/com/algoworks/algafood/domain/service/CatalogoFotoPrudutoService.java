package com.algoworks.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algoworks.algafood.domain.model.FotoProduto;
import com.algoworks.algafood.domain.repository.ProdutoRepository;
import com.algoworks.algafood.domain.service.FotoStorageService.NovaFoto;
import com.algoworks.algafood.infrastructere.service.storage.LocalFotoStorageService;

import jakarta.transaction.Transactional;

@Service
public class CatalogoFotoPrudutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private LocalFotoStorageService fotoStorageService;
	
	@Transactional
	public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
		
		Long restauranteId = foto.getRestauranteId();
		Long produtoId = foto.getProduto().getId();
		
		String nomeNovoArquivo = fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo());
		String nomeArquivoExistente = null;
		
		Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);
		
		if(fotoExistente.isPresent()) {
			
			nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
			
			produtoRepository.delete(fotoExistente.get());
			
		}
		
		foto.setNomeArquivo(nomeNovoArquivo);
		
		//Primeiro salva e depois armazena
		foto =  produtoRepository.save(foto);
		produtoRepository.flush();
		
		NovaFoto novaFoto = NovaFoto.builder()
				.nomeArquivo(foto.getNomeArquivo())
				.inputStream(dadosArquivo)
				.build();
		
		if(nomeArquivoExistente != null) {
			
		}
		//Armazenando no banco
		fotoStorageService.substituir(nomeArquivoExistente,novaFoto);
		
		return foto;
	}
}
