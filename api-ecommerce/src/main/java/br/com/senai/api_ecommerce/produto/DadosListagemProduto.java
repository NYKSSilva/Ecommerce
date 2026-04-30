package br.com.senai.api_ecommerce.produto;

import java.math.BigDecimal;

public record DadosListagemProduto(
        String nome,
        BigDecimal preco,
        Long estoque,
        String nomeCategoria
) {
    public DadosListagemProduto(Produto produto){
        this(
                produto.getNome(),
                produto.getPreco(),
                produto.getEstoque(),
                produto.getCategoria().getNome()
        );
    }
}
