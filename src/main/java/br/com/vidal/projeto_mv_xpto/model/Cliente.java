package br.com.vidal.projeto_mv_xpto.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Cliente {
  @Id
  @GeneratedValue(generator = "cliente_seq")
  @Column(name = "id_cliente")
  private Integer idCliente;
  private String nome;
  @Column(name = "cpf_cnpj")
  private String cpfCnpj;
  private String endereco;
  private String bairro;
  private String cidade;
  private Integer numero;
  private Integer cep;
  private String complemento;
  private String telefone;
  @Column(name = "data_cadastro")
  private LocalDate dataCadastro;
  @Column(name = "saldo_inicial")
  private Double saldoInicial;
}
