package br.com.vidal.projeto_mv_xpto.service.contaBancaria;

import java.util.List;
import java.util.Optional;

import br.com.vidal.projeto_mv_xpto.shared.ContaBancariaDto;

public interface ContaBancariaService {

  List<ContaBancariaDto> retornaTodos();

  Optional<ContaBancariaDto> retornaPorId(Integer id);

  ContaBancariaDto gravarContaBancaria(ContaBancariaDto request);

  ContaBancariaDto atualizarContaBancaria(Integer id, ContaBancariaDto request);

  String apagarContaBancaria(Integer id);
  
}
