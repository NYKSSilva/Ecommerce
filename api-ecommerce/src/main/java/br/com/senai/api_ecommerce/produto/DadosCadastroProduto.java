package br.com.senai.api_ecommerce.produto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record DadosCadastroProduto(
        @NotBlank
        @Size(min=3,max=100)
        String nome,

        @NotNull
        @Positive
        @Digits(integer = 10, fraction = 2)
        BigDecimal preco,

        @NotBlank
        @Pattern(regexp = "^\\S{1,20}$", message = "SKU não pode conter espaço em branco")
        @Size(max=20)
        String sku,

        @Size(max=255)
        String descricao,

        @NotNull
        @PositiveOrZero
        Long estoque,

        @NotNull
        Long categoriaId
) {
}
