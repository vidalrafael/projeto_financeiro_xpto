package br.com.vidal.projeto_mv_xpto.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContaClienteCast implements Serializable {
  private static final long serialVersionUID = 1L;

  @ManyToOne
  @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
  private Cliente cliente;

  @ManyToOne
  @JoinColumn(name = "id_conta_bancaria", referencedColumnName = "id_conta_bancaria")
  private ContaBancaria contaBancaria;
}
