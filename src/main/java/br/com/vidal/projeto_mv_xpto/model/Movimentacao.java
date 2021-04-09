package br.com.vidal.projeto_mv_xpto.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "movimentacao")
public class Movimentacao {
  @Id
  @GeneratedValue(generator = "movimentacao_seq")
  @Column(name = "id_movimentacao")
  private Integer idMovimentacao;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
  private Cliente cliente;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "id_conta_bancaria", referencedColumnName = "id_conta_bancaria")
  private ContaBancaria contaBancaria;

  private Double valor;
  private LocalDate data;  
}
