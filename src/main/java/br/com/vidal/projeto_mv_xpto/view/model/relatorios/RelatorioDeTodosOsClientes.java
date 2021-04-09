package br.com.vidal.projeto_mv_xpto.view.model.relatorios;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RelatorioDeTodosOsClientes {
  private String nome;
  private LocalDate dataCadastro;
  private double saldo;
}
