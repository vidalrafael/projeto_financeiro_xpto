package br.com.vidal.projeto_mv_xpto.shared;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import br.com.vidal.projeto_mv_xpto.model.Cliente;
import br.com.vidal.projeto_mv_xpto.model.ContaBancaria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MovimentacaoDto {
  private Integer idMovimentacao;
  @NotNull(message = "O cliente deve ser preenchido")
  private Cliente cliente;
  @NotNull(message = "A conta bancaria deve ser preenchida")
  private ContaBancaria contaBancaria;
  private Double valor;
  private LocalDate data; 
}  
