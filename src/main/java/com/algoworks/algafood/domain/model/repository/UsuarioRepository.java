package com.algoworks.algafood.domain.model.repository;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.algoworks.algafood.domain.model.Usuario;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {

	//Criando uma busca por email
	Optional<Usuario> findByEmail(String email);
}
