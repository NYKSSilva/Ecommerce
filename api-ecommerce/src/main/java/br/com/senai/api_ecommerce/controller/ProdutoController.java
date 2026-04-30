package br.com.senai.api_ecommerce.controller;

import br.com.senai.api_ecommerce.categoria.CategoriaRepository;
import br.com.senai.api_ecommerce.produto.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("produtos")
public class ProdutoController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping
    @Transactional
    public void cadastrarProduto(@RequestBody @Valid DadosCadastroProduto dados){
        //1. Verificar se a categoria existe
        var categoria = categoriaRepository.findByIdAndAtivoTrue(dados.categoriaId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Categoria não encontrada"));
        //2. Verificar se SKU é único
        if(produtoRepository.existsBySku(dados.sku()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "SKU já cadastrado no sistema");
        //3. Criar o produto
        Produto produto = new Produto(dados, categoria);
        produtoRepository.save(produto);
    }

    @GetMapping
    public Page<DadosListagemProduto> listarProdutos(@PageableDefault(size=10, sort={"nome"}) Pageable paginacao){
        return produtoRepository.findAllByAtivoTrue(paginacao)
                .map(DadosListagemProduto::new);
    }

    @GetMapping("/{id}")
    public DadosDetalhamentoProduto buscarProdutoPorId(@PathVariable Long id){
        var produto = produtoRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
        return new DadosDetalhamentoProduto(produto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluirProduto(@PathVariable Long id){
        var produto = produtoRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
        produto.excluirProduto();

        return ResponseEntity
    }

    public void atualizaProduto(
            @RequestBody @Valid DadosAtualizarProduto dados
    ){
        var produto = produtoRepository.findByIdAndAtivoTrue(dados.id()).
    }
}