package br.com.vidal.projeto_mv_xpto.view.model;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestInstituicaoFinanceira {
  @NotBlank(message = "O CPF/CNPJ deve ser preenchido")
  private String cpfCnpj;
  @NotBlank(message = "O código do banco deve ser preenchido")
  private String codigoBanco;
  @NotNull(message = "O número da conta deve ser preenchido")
  private Integer numeroConta;
  @NotNull(message = "A agencia deve ser preenchida")
  private Integer agencia;
  @NotNull(message = "A data deve ser preenchida")
  private LocalDate dataMovimentacao;
  @NotNull(message = "O valor deve ser preenchido")
  private Double valor;
}
