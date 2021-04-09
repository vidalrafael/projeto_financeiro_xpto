package br.com.vidal.projeto_mv_xpto.service.contaCliente;

import java.util.List;

import br.com.vidal.projeto_mv_xpto.shared.ContaClienteDto;

public interface ContaClienteService {

  List<ContaClienteDto> retornaContasPorCliente(Integer idCliente);

  ContaClienteDto gravaContaCliente(Integer idCliente, Integer idContaBancaria);

  String apagarContaCliente(Integer idCliente, Integer idContaBancaria);
  
}
