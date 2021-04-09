package br.com.vidal.projeto_mv_xpto.view.model.relatorios;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RelatorioDeReceitaDaEmpresaAuxiliar {
  private String nome;
  private Integer quantidadeMovimentacoes;
  private double valorMovimentacoes;
}
