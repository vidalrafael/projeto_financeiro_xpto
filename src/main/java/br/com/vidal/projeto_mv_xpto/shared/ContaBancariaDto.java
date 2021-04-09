package br.com.vidal.projeto_mv_xpto.shared;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContaBancariaDto {
  private Integer idContaBancaria;
  private String nome;
  @NotBlank(message = "O código do banco deve ser preenchido")
  private String codigoBanco;
  @NotNull(message = "O número da conta deve ser preenchido")
  private Integer numeroConta;
  @NotNull(message = "A agencia deve ser preenchida")
  private Integer agencia;  
}
