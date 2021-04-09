package br.com.vidal.projeto_mv_xpto.service.cliente;

import java.util.List;
import java.util.Optional;

import br.com.vidal.projeto_mv_xpto.shared.ClienteDto;

public interface ClienteService {

  List<ClienteDto> retornaTodos();

  Optional<ClienteDto> retornaPorid(Integer id);

  ClienteDto salvarCliente(ClienteDto request);

  ClienteDto alterarCliente(Integer id, ClienteDto clienteDto);

  String apagarCliente(Integer id);  
  
}
