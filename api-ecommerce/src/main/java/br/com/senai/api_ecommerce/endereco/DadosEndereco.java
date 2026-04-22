package br.com.senai.api_ecommerce.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(
        @NotBlank
         String logradouro,

         @NotBlank
         String bairro,

         @NotBlank
         String cidade,

         @NotBlank
         @Pattern(regexp = "\\d{8}")
         String cep,

         @NotBlank
         @Pattern(regexp = "[A-Z]{2}")
         String uf,

         String numero,

         String complemento


) {
}
