package com.danilo.cadastro_usuario.business;

import com.danilo.cadastro_usuario.infrastructure.entity.Usuario;
import com.danilo.cadastro_usuario.infrastructure.exceptions.EmailJaCadastradoException;
import com.danilo.cadastro_usuario.infrastructure.exceptions.UsuarioNaoEncontradoException;
import com.danilo.cadastro_usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;

    public Usuario cadastraUsuario(Usuario usuario) {

        usuario.setEmail(usuario.getEmail().trim().toLowerCase());

        if(repository.existsByEmail(usuario.getEmail())) {
            throw new EmailJaCadastradoException("Email já cadastrado");
        }

        return repository.save(usuario);
    }

    public List<Usuario> listarTodosUsuarios() {
        return repository.findAll();
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return repository.findByEmail(email) // Procure no banco um usuário com esse email.
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuario não encontrado!")); // Se encontrar, devolva o usuário. Se não encontrar, lance um erro dizendo que o usuário não foi encontrado.
    }

    public Usuario atualizarUsuario(String email, Usuario novoUsuario) {

        Usuario usuario = buscarUsuarioPorEmail(email);

        if (!usuario.getEmail().equals(novoUsuario.getEmail())) {

            if (repository.existsByEmail(novoUsuario.getEmail())) {
                throw new EmailJaCadastradoException("Novo email já está em uso");
            }
        }

        usuario.setNome(novoUsuario.getNome());
        usuario.setEmail(novoUsuario.getEmail());

        return repository.save(usuario);
    }

    public void deletarPorEmail(String email) {

        Usuario usuario = buscarUsuarioPorEmail(email);

        repository.deleteByEmail(email);
    }
}
