package br.com.senai.api_ecommerce.produto;

import java.math.BigDecimal;

public record DadosDetalhamentoProduto(
        Long id,
        String nome,
        BigDecimal preco,
        String sku,
        String descricao,
        Long estoque,
        String nomeCategoria
) {
    public DadosDetalhamentoProduto(Produto produto){
        this(
                produto.getId(),
                produto.getNome(),
                produto.getPreco(),
                produto.getSku(),
                produto.getDescricao(),
                produto.getEstoque(),
                produto.getCategoria().getNome()
        );
    }
}
