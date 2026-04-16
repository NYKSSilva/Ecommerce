package br.com.senai.api_ecommerce.cliente;

public record DadosDetalhamentoCliente(
        Long id,
        String nome,
        String email,
        String cpf,
        String telefone
) {
    public DadosDetalhamentoCliente(Cliente cliente){
        this(cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getCpf(), cliente.getTelefone());
    }
}
