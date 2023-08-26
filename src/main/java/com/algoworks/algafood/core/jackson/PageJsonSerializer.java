package com.algoworks.algafood.core.jackson;

import java.io.IOException;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;


@JsonComponent //IMPLEMENTA A SERIALIZAÇÃO PERSONALIZADO DO JSON.
public class PageJsonSerializer extends JsonSerializer<Page<?>> {
	// O Page<?> é o tipo e o ? É para qualquer tipo ex: Pedido, Restaurante etc...
	
	//Metodo Obrigatorio de SubInscrição
	@Override
	public void serialize(Page<?> value, JsonGenerator gen, 
			SerializerProvider serializers) throws IOException {
		
		//FAZENDO AS CUSTOMIZAÇÃO DA RESPOSTA PARA API.
		
		//Iniciando o Objeto de Resposta.
		gen.writeStartObject();
		
		//O nome content pode ser qualquer coisa (CONTEUDO)
		gen.writeObjectField("content", value.getContent() );
		
		//Propriedades da paginação: Pode ser qualquer nomes.
		gen.writeNumberField("size",value.getSize()); //TOTAL DE ELEMENTO POR PAGINAS
		
		gen.writeNumberField("totalElements",value.getTotalElements()); // TOTAL DE ELEMENTO
		
		gen.writeNumberField("totalPages",value.getTotalPages());// TOTAL DE PAGINAS
		
		gen.writeNumberField("number",value.getNumber());//PAGINA ATUAL
		
		//Finalizando o Objeto de Resposta.
		gen.writeEndObject();

	}

	
}
