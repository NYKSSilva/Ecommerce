package br.com.senai.api_ecommerce.controller;

import br.com.senai.api_ecommerce.categoria.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@RestController
@RequestMapping("categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository repository;

    @PostMapping
    @Transactional
    public void cadastrarCategoria(@RequestBody @Valid DadosCadastroCategoria dados) {
        repository.save(new Categoria(dados));
    }

    @GetMapping
    public Page<DadosListagemCategoria> listarCategorias(@PageableDefault(size=10, sort= {"nome"}) Pageable paginacao){
        return repository.findAllByAtivoTrue(paginacao)
                .map(DadosListagemCategoria::new);
    }

    @PutMapping
    @Transactional
    public void atualizarcategoria(@RequestBody DadosAtualizarCategoria dados){
        var categoria = repository.getReferenceById(dados.id());
        categoria.atualizarCategoria(dados);

    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deletarCategoria(@PathVariable Long id){
//        repository.deleteById(id); (so deleta porém o certo é "excluir" desativando
        var categoria = repository.getReferenceById(id);
        categoria.excluirCategoria();
    }

    @GetMapping("/{id}")
    public DadosDetalhamentoCategoria detalharCategoria (@PathVariable Long id){
        Categoria categoria = repository.findByIdAndAtivoTrue(id)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Categoria não existe"));
        return  new DadosDetalhamentoCategoria(categoria);
    }
}
