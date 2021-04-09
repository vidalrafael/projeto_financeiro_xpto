package br.com.vidal.projeto_mv_xpto.view.model.relatorios;

import java.time.LocalDate;

import br.com.vidal.projeto_mv_xpto.shared.ClienteDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RelatorioDeSaldoDoCliente {
  private LocalDate dataInicial;
  private LocalDate dataFinal;
  private ClienteDto cliente;
  private double movimentacaoCredito;
  private double movimentacaoDebito;
  private double totalMovimentacao;
  private Integer quantidadeMovimentacoes;
  private double valorCobradoPelasMovimentacoes;
  private double valorTotalPagoPelasMovimentacoes;
  private double saldoAtual;
}
