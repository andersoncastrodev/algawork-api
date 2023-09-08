package com.algoworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algoworks.algafood.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {
	
	//Importante. Para criar pesquisa de forma personalizada. 
	// Por ser pelo atributo da Classe Ex: nome
	// Ou customizada ex:  findByNome : findBy = Prefixo depois o Nome É o atribuito
	// Ou pode ser Assim:  findQualquerNomeByNome : O qualquer nome pode ser um nome melhor do metodo. 

	List<Cozinha> findTodasByNomeContaining (String nome);
	
	Optional<Cozinha> findSomenteUmaCozinhaByNome(String nome); 
	
	//Key Words = Palavras Chaves
	// Containing  = busca usando o Like ' % nome % '
	
	//Consulta nome exato, mas poderia usar o Containing.
	//Retorna True se o nome existe. KeyWord = exists
	boolean existsByNome(String nome);
	
}
