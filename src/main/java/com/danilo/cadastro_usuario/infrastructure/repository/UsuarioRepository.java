package com.danilo.cadastro_usuario.infrastructure.repository;

import com.danilo.cadastro_usuario.infrastructure.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    void deleteByEmail(String email);

    boolean exitsByEmail(String email);
}
