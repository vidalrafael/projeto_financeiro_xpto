package br.com.vidal.projeto_mv_xpto.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "conta_bancaria")
public class ContaBancaria {
  @Id
  @GeneratedValue(generator = "conta_bancaria_seq")
  @Column(name = "id_conta_bancaria")
  private Integer idContaBancaria;
  private String nome;
  @Column(name = "codigo_banco")
  private String codigoBanco;
  @Column(name = "numero_conta")
  private Integer numeroConta;
  private Integer agencia;  
}
