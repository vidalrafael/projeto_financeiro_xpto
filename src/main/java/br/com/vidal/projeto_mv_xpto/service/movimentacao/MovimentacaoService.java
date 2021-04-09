package br.com.vidal.projeto_mv_xpto.service.movimentacao;

import java.util.List;
import java.util.Optional;

import br.com.vidal.projeto_mv_xpto.shared.MovimentacaoDto;
import br.com.vidal.projeto_mv_xpto.view.model.RequestInstituicaoFinanceira;

public interface MovimentacaoService {

  List<MovimentacaoDto> retornaTodos();

  Optional<MovimentacaoDto> retornaPorId(Integer id);

  MovimentacaoDto gravarMovimentacao(RequestInstituicaoFinanceira request);
  
}
