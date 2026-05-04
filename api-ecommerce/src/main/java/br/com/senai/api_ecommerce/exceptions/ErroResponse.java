package br.com.senai.api_ecommerce.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Erro de validação")

public record ErroResponse(
        @Schema(example = "CATEGORIA_NAO_ENCONTRADA") String codigo,
        @Schema(example = "Categoria inválida")       String mensagem
) {
}
