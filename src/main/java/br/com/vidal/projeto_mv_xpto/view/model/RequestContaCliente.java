package br.com.vidal.projeto_mv_xpto.view.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestContaCliente {
  private Integer idCliente;
  private Integer idContaBancaria;
}
