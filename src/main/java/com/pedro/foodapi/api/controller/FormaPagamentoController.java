package com.pedro.foodapi.api.controller;

import com.pedro.foodapi.api.assembler.FormaPagamentoInputDesAssembler;
import com.pedro.foodapi.api.assembler.FormaPagamentoModelAssembler;
import com.pedro.foodapi.api.model.FormaPagamentoModel;
import com.pedro.foodapi.api.model.input.FormaPagamentoInput;
import com.pedro.foodapi.domain.model.FormaPagamento;
import com.pedro.foodapi.domain.repository.FormaPagamentoRepository;
import com.pedro.foodapi.domain.service.CadastroFormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamento;

    @Autowired
    private FormaPagamentoModelAssembler formaPagamentoModelAssembler;

    @Autowired
    private FormaPagamentoInputDesAssembler formaPagamentoInputDesAssembler;

    @GetMapping
    public List<FormaPagamentoModel> listar(){
        List<FormaPagamento> todasFormasPagamento = formaPagamentoRepository.findAll();

        return formaPagamentoModelAssembler.toCollectionModel(todasFormasPagamento);
    }

    @GetMapping("/{id}")
    public FormaPagamentoModel buscar(@PathVariable Long id){
        FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(id);

        return formaPagamentoModelAssembler.toModel(formaPagamento);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput){
        FormaPagamento formaPagamento = formaPagamentoInputDesAssembler.toDomainObject(formaPagamentoInput);
        formaPagamento = cadastroFormaPagamento.salvar(formaPagamento);
        return formaPagamentoModelAssembler.toModel(formaPagamento);
    }

    @PutMapping("/{id}")
    public FormaPagamentoModel atualizar(@PathVariable Long id, @RequestBody @Valid FormaPagamentoInput formaPagamentoInput){
        FormaPagamento formaPagamentoAtual = cadastroFormaPagamento.buscarOuFalhar(id);

        formaPagamentoInputDesAssembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);
        formaPagamentoAtual = cadastroFormaPagamento.salvar(formaPagamentoAtual);

        return formaPagamentoModelAssembler.toModel(formaPagamentoAtual);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id){
        cadastroFormaPagamento.excluir(id);
    }







}
