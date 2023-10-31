package com.pedro.foodapi.api.controller;

import com.pedro.foodapi.api.assembler.UsuarioInputDesAssembler;
import com.pedro.foodapi.api.assembler.UsuarioModelAssembler;
import com.pedro.foodapi.api.model.UsuarioModel;
import com.pedro.foodapi.api.model.input.SenhaInput;
import com.pedro.foodapi.api.model.input.UsuarioInput;
import com.pedro.foodapi.api.model.input.UsuarioPasswordInput;
import com.pedro.foodapi.domain.model.Usuario;
import com.pedro.foodapi.domain.repository.UsuarioRepository;
import com.pedro.foodapi.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @Autowired
    private UsuarioInputDesAssembler usuarioInputDesAssembler;

    @GetMapping
    public List<UsuarioModel> listar(){
        return usuarioModelAssembler.toCollectionModel(usuarioRepository.findAll());
    }

    @GetMapping("/{id}")
    public UsuarioModel buscar(@PathVariable Long id){
        return usuarioModelAssembler.toModel(cadastroUsuario.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioModel adicionar(@RequestBody @Valid UsuarioPasswordInput usuarioInput){
        Usuario usuario = usuarioInputDesAssembler.toDomainObject(usuarioInput);
        usuario = cadastroUsuario.salvar(usuario);

        return usuarioModelAssembler.toModel(usuario);
    }

    @PutMapping("/{id}")
    public UsuarioModel atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioInput usuarioInput){
        Usuario usuarioAtual = cadastroUsuario.buscarOuFalhar(id);
        usuarioInputDesAssembler.copyToDomainObject(usuarioInput, usuarioAtual);
        usuarioAtual = cadastroUsuario.salvar(usuarioAtual);

        return usuarioModelAssembler.toModel(usuarioAtual);
    }

    @PutMapping("/{id}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long id, @RequestBody @Valid SenhaInput senhaInput){
        cadastroUsuario.alterarSenha(id, senhaInput.getSenhaAtual(), senhaInput.getNovaSenha());
    }





}
