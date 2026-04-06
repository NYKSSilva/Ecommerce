package br.com.senai.api_ecommerce.categoria;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name="categorias")
@Entity(name="Categoria")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")

public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String descricao;
    private boolean ativo;

    public Categoria(DadosCadastroCategoria dados){
        this.nome = dados.nome();
        this.descricao = dados.descricao();
        this.ativo = true;
    }

    public void atualizarCategoria(DadosAtualizarCategoria dados) {
        if(dados.nome() !=null && !dados.nome().isBlank())
         this.nome = dados.nome();

        if(dados.descricao() !=null)
            this.descricao = dados.descricao();
    }

    public void excluirCategoria() {
        this.ativo = false;
    }
}
