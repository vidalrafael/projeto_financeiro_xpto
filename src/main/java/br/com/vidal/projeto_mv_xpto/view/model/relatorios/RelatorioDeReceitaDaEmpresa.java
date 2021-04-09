package br.com.vidal.projeto_mv_xpto.view.model.relatorios;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RelatorioDeReceitaDaEmpresa {
  private LocalDate dataInicial;
  private LocalDate dataFinal;
  private List<RelatorioDeReceitaDaEmpresaAuxiliar> cliente;
  private double totalReceita;
}
