package com.pedro.foodapi.domain.service;

import com.pedro.foodapi.domain.exception.NegocioException;
import com.pedro.foodapi.domain.exception.UsuarioNaoEncontradoException;
import com.pedro.foodapi.domain.model.Usuario;
import com.pedro.foodapi.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void alterarSenha(Long id, String senhaAtual, String novaSenha){
        Usuario usuario = buscarOuFalhar(id);
        if (usuario.senhanaoCoincideCom(senhaAtual)){
            throw new NegocioException("Senha atual informada não coincide com senha do usuario.");
        }
        usuario.setSenha(novaSenha);
    }

    public Usuario buscarOuFalhar(Long id){
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }
}
