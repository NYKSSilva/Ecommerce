package br.com.senai.api_ecommerce.cliente;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record DadosAtualizarCliente(
        Long id,

        @NotBlank
        @Size(min= 3, max=100)
        @Column(unique=true)
        String nome,

        @NotBlank
        @Column(unique = true)
        @Email
        String email,

        @Column(unique = true)
        @Size(min= 11, max=11)
        @CPF
        String cpf,

        @Size(max=20)
        String telefone

) {
}
