package br.com.senai.api_ecommerce.endereco;

import jakarta.validation.constraints.Pattern;

public record DadosAtualizarEndereco(
        String logradouro,
        String bairro,
        String cidade,

        @Pattern(regexp = "\\d{8}")
        String cep,

        @Pattern(regexp = "[a-zA-Z]{2}")
        String uf,

        String numero,
        String complemento

) {
}
