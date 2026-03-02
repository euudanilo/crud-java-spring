package com.danilo.cadastro_usuario.business;

import com.danilo.cadastro_usuario.infrastructure.entity.Usuario;
import com.danilo.cadastro_usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;

    public Usuario cadastraUsuario(Usuario usuario) {
        repository.findByEmail(usuario.getEmail())
                .ifPresent(u -> {
                    throw new RuntimeException("Email já cadastrado");
                });

        return repository.save(usuario);
    }

    public List<Usuario> listarTodosUsuarios() {
        return repository.findAll();
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario não encotrado!"));
    }

    public Usuario atualizarUsuario(String email, Usuario novoUsuario) {
        Usuario usuario = buscarUsuarioPorEmail(email);

        usuario.setNome(novoUsuario.getNome());
        usuario.setEmail(novoUsuario.getEmail());

        return repository.save(usuario);
    }

    public void deletarPorEmail(String email) {
        repository.deleteByEmail(email);
    }
}
