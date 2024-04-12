package com.algoworks.algafood.infrastructere.service.storage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.algoworks.algafood.domain.service.FotoStorageService;

@Service
public class LocalFotoStorageService implements FotoStorageService {

	//Pegar o valor lá do application.properties
	@Value("${algafood.storage.local.diretorio-fotos}")
	private Path diretorioFotos;
	
	@Override
	public void armazenar(NovaFoto novaFoto) {
		
		try {
			
			Path arquivoPath = getArquivoPath( novaFoto.getNomeArquivo());
			
			FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));
		
		} catch (IOException e) {
		
			throw new StorageException("Não foi possivel armazenar arquivo.",e);
		}
		
			novaFoto.getInputStream();
	}


	private Path getArquivoPath(String nomeArquivo) {
		//return diretorioFotos.resolve(Path.of(nomeArquivo));
		return null;
	}


	@Override
	public void remover(String nomeArquivo) {
	
	try {
		
		Path arquivoPath = getArquivoPath(nomeArquivo);
		Files.deleteIfExists(arquivoPath);
		
	} catch (IOException e) {
		
		throw new StorageException("Não foi possivel Excluir arquivo.",e);
	}
	
	}

	@Override
	public InputStream recuperar(String nomeArquivo) {
		   try {
		        Path arquivoPath = getArquivoPath(nomeArquivo);

		        return Files.newInputStream(arquivoPath);
		    } catch (Exception e) {
		        throw new StorageException("Não foi possível recuperar arquivo.", e);
		    }
	}

}
