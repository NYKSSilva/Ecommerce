package br.com.senai.api_ecommerce.cliente;

import br.com.senai.api_ecommerce.endereco.Endereco;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name= "clientes")
@Entity(name="Cliente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")

public class Cliente {
 @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String nome;
  private String email;
  private String cpf;
  private String telefone;
  private boolean ativo;

  @Embedded //configura a classe endereço como parte do cliente
  private Endereco endereco;

 public Cliente(DadosCadastroCliente dados){
  this.nome = dados.nome();
  this.email = dados.email();
  this.cpf = dados.cpf();
  this.telefone = dados.telefone();
  this.ativo = true;
  this.endereco = new Endereco (dados.endereco());
 }

 public void atualizarCliente(DadosAtualizarCliente dados){
  if(dados.nome() != null && !dados.nome().isBlank())
   this.nome = dados.nome();
  if(dados.email() != null && !dados.email().isBlank())
   this.email = dados.email();
  if(dados.telefone() !=null && dados.telefone().isBlank())
   this.telefone = dados.telefone();
  if(dados.endereco() !=null)
   this.endereco.atualizarEndereco(dados.endereco());

 }

 public void excluirCliente(){
  this.ativo = false;
 }
}
