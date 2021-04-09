package br.com.vidal.projeto_mv_xpto.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "conta_cliente")
public class ContaCliente {
  @EmbeddedId
  private ContaClienteCast contaClienteCast;
}
