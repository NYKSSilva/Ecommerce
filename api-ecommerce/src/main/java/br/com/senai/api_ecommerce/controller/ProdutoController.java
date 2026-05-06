package br.com.senai.api_ecommerce.controller;

import br.com.senai.api_ecommerce.categoria.Categoria;
import br.com.senai.api_ecommerce.categoria.CategoriaRepository;
import br.com.senai.api_ecommerce.produto.*;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

//http://localhost:8080/swagger-ui/index.html


@RestController
@RequestMapping("produtos")
@Tag(name="Produtos", description = "Gerenciamento dos produtos do ecommerce")
@OpenAPIDefinition(tags ={
        @Tag(name = "Criar Produto",description = "Criação de produtos"),
        @Tag(name = "Listar todos os produtos",description = "Listagem de todos os produtos"),
        @Tag(name = "Listar Produto por ID",description = "Listagem de produtos especificos"),
        @Tag(name = "Excluir Produto",description = "Excluir produto"),
        @Tag(name = "Atualizar Produto",description = "Atualizar produto")
})
public class ProdutoController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping
    @Transactional
    @Operation(summary = "Criar um novo produto")
    @Tag(name = "Criar produto", description = "Salva os dados do produto no Banco de dados")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DadosDetalhamentoProduto.class))
                    }),
            @ApiResponse(responseCode = "409", description = "SKU já cadastrado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Categoria inválida", content = @Content)
    })
    public ResponseEntity<DadosDetalhamentoProduto> cadastrarProduto(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DadosCadastroProduto.class),
                            examples = @ExampleObject(
                                    value = "{ \"nome\": \"Nome Produto\",\t\n" +
                                            "\t\"preco\": 21.00,\n" +
                                            "\t\"sku\":\"999999999\",\n" +
                                            "\t\"descricao\": \"Descrição do produto\",\n" +
                                            "\t\"estoque\": 1,\n" +
                                            "\t\"categoriaId\": 6}"
                            )
                    )
            )
            @RequestBody @Valid DadosCadastroProduto dados){
        var categoria = categoriaRepository.findByIdAndAtivoTrue(dados.categoriaId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Categoria não encontrada"));
        if(produtoRepository.existsBySkuAndAtivoTrue(dados.sku()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "SKU já cadastrado no sistema");
        Produto produto = new Produto(dados, categoria);
        produtoRepository.save(produto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new DadosDetalhamentoProduto(produto));
    }

    @GetMapping
    @Operation(summary = "Listar produtos")
    @Tag(name = "Listar todos os produtos", description = "Lista todos os produtos aivos no banco de dados")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Listagem ocorreu com sucesso",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DadosDetalhamentoProduto.class))
                    })
    })
    public ResponseEntity<Page<DadosListagemProduto>> listarProdutos(@PageableDefault(size=10, sort={"nome"}) Pageable paginacao){
        var page = produtoRepository.findAllByAtivoTrue(paginacao)
                .map(DadosListagemProduto::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Listar um produto especifico")
    @Tag(name = "Listar Produto por ID", description = "Listagem expecifica de acordo com o id ")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Listagem ocorreu com sucesso",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DadosDetalhamentoProduto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado no sistema", content = @Content),


    })

    public ResponseEntity<DadosDetalhamentoProduto> buscarProdutoPorId(@PathVariable Long id){
        var produto = produtoRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
        return ResponseEntity.ok( new DadosDetalhamentoProduto(produto));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir um produto")
    @Tag(name = "Excluir Produto")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204", description = "Produto excluido com sucesso",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DadosDetalhamentoProduto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado no sistema", content = @Content),


    })
    public ResponseEntity excluirProduto(@PathVariable Long id){
        var produto = produtoRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
        produto.excluirProduto();

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Atualizar um produto")
    @Tag(name = "Atualizar Produto")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DadosDetalhamentoProduto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado no sistema \\t\\ Categoria não encontrada no sistema ", content = @Content),
            @ApiResponse(responseCode = "409", description = "SKU já cadastrado no sistema", content = @Content),


    })
    public ResponseEntity<DadosDetalhamentoProduto> atualizaProduto(
            @RequestBody @Valid DadosAtualizarProduto dados
    ){
        var produto = produtoRepository.findByIdAndAtivoTrue(dados.id())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));

        Categoria categoria = null;
        if(dados.categoriaId() != null) {
            categoria = categoriaRepository.findByIdAndAtivoTrue(dados.categoriaId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));
        }
        if(dados.sku()!=null && !dados.sku().isBlank()) {
            if (produtoRepository.existsBySkuAndAtivoTrue(dados.sku()))
                throw new ResponseStatusException(HttpStatus.CONFLICT, "SKU já cadastrado no sistema");
        }

        produto.atualizarProduto(dados, categoria);

        return ResponseEntity.ok(new DadosDetalhamentoProduto(produto));
    }
}