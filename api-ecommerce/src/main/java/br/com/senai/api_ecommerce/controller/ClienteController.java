package br.com.senai.api_ecommerce.controller;

import br.com.senai.api_ecommerce.cliente.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("Clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository repository;

    //dto(Data Transfer Object)= intermediario que filtra as informações para a segurança do codigo/banco de dados/servidor
    @PostMapping
    @Transactional
    public void cadastrarCliente(@RequestBody @Valid DadosCadastroCliente dados){
        repository.save(new Cliente(dados));
    }

    @GetMapping
    public Page<DadosListagemCliente> listarClientes(@PageableDefault(size=6, sort= {"nome"}) Pageable paginacao){
        return repository.findAllByAtivoTrue(paginacao)
                .map(DadosListagemCliente::new);
    }

    @PutMapping
    @Transactional
    public void atualizarCliente(@RequestBody @Valid DadosAtualizarCliente dados){
        var cliente = repository.getReferenceById(dados.id());
        cliente.atualizarCliente(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deletarCliente(@PathVariable Long id){
        var cliente = repository.getReferenceById(id);
        cliente.excluirCliente();
    }

    @GetMapping("/{id}")
    public DadosDetalhamentoCliente detalharCliente(@PathVariable Long id){
        var cliente = repository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                "Cliente não existe." ));
        return new DadosDetalhamentoCliente(cliente);
    }
}
