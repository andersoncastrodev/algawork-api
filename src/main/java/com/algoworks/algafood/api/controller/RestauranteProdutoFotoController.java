package com.algoworks.algafood.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.algoworks.algafood.api.assembler.FotoProdutoModelAssembler;
import com.algoworks.algafood.api.model.input.FotoProdutoInput;
import com.algoworks.algafood.api.modelDTO.FotoProdutoDTO;
import com.algoworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algoworks.algafood.domain.model.FotoProduto;
import com.algoworks.algafood.domain.model.Produto;
import com.algoworks.algafood.domain.service.CadastroProdutoService;
import com.algoworks.algafood.domain.service.CatalogoFotoPrudutoService;
import com.algoworks.algafood.domain.service.FotoStorageService;


import javax.validation.Valid;

@RestController
@RequestMapping("/restaurante/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

	@Autowired
	private CadastroProdutoService cadastroProdutoService;
	
	@Autowired
	private CatalogoFotoPrudutoService catalogoFotoPrudutoService;
	
	@Autowired
	private FotoProdutoModelAssembler fotoProdutoModelAssembler;
	
	@Autowired
	private FotoStorageService fotoStorageService;
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoDTO atualizarFoto(@PathVariable Long restauranteId, 
		@PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput) throws IOException {
	
		Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);
		
		MultipartFile arquivo = fotoProdutoInput.getArquivo();
		
		FotoProduto fotoProduto = new FotoProduto();
		fotoProduto.setProduto(produto);
		fotoProduto.setDescricao(fotoProdutoInput.getDescricao());
		fotoProduto.setContentType(arquivo.getContentType());
		fotoProduto.setTamanho(arquivo.getSize());
		fotoProduto.setNomeArquivo(arquivo.getOriginalFilename());
		
	
		FotoProduto fotoSalva = catalogoFotoPrudutoService.salvar(fotoProduto, arquivo.getInputStream());
		
		return fotoProdutoModelAssembler.toModelDTO(fotoSalva);
		
		
		
		
// Teste do Uplado de arquivos		
//		var nomearquivo = UUID.randomUUID().toString()+"_"+ fotoProdutoInput.getArquivo().getOriginalFilename();
//		
//		var arquivoFoto = Path.of("/home/anderson/Imagens/UploadAlgaWorks",nomearquivo);
//		
//		System.out.println(fotoProdutoInput.getDescricao());
//		System.out.println(arquivoFoto);
//		System.out.println(fotoProdutoInput.getArquivo().getContentType());
//	
//	try {		
//		fotoProdutoInput.getArquivo().transferTo(arquivoFoto);
//	
//	} catch (Exception e) {
//		e.printStackTrace();
//	}
		
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoProdutoDTO buscar(@PathVariable Long restauranteId, 
	        @PathVariable Long produtoId) {
	    FotoProduto fotoProduto = catalogoFotoPrudutoService.buscarOuFalhar(restauranteId, produtoId);
	    
	    return fotoProdutoModelAssembler.toModelDTO(fotoProduto);
	}	
	
	@GetMapping
	public ResponseEntity<InputStreamResource> servirFoto(@PathVariable Long restauranteId, 
			@PathVariable Long produtoId, @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
		
		try {
			
		FotoProduto fotoProduto = catalogoFotoPrudutoService.buscarOuFalhar(restauranteId, produtoId);
		
		MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
		
		List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(fotoProduto.getContentType());
		
		verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypesAceitas);
		
		InputStream inputStream = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
		
		return ResponseEntity.ok()
				.contentType(mediaTypeFoto)
				.body(new InputStreamResource(inputStream));
		
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long restauranteId, 
	        @PathVariable Long produtoId) {
		catalogoFotoPrudutoService.excluir(restauranteId, produtoId);
	}   

	

	private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {
		boolean compativel = mediaTypesAceitas.stream()
				.anyMatch(mediaTypeAceitas -> mediaTypeAceitas.isCompatibleWith(mediaTypeFoto));
		if(!compativel) {
			throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
		}

	}
	
	
}
