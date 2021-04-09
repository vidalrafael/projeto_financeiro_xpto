package br.com.vidal.projeto_mv_xpto.view.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestAtualizaCliente {
  private String nome;
  private String endereco;
  private String bairro;
  private String cidade;
  private Integer numero;
  private Integer cep;
  private String complemento;
  private String telefone;
}
