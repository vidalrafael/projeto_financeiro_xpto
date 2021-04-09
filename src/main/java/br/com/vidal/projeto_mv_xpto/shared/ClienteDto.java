package br.com.vidal.projeto_mv_xpto.shared;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClienteDto {
  private Integer idCliente;
  private String nome;
  @NotBlank(message = "O CPF/CNPJ deve ser preenchido")
  private String cpfCnpj;
  private String endereco;
  private String bairro;
  private String cidade;
  private Integer numero;
  private Integer cep;
  private String complemento;
  private String telefone;
  private LocalDate dataCadastro;
  @NotNull(message = "O saldo inicial deve ser preenchido")
  private Double saldoInicial;
  @NotNull(message = "A(s) conta(s) deve(m) ser preenchida(s)")
  private List<ContaBancariaDto> contasBancarias;
}
