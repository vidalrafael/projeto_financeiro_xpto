package br.com.vidal.projeto_mv_xpto.service.movimentacao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vidal.projeto_mv_xpto.exception.ErroServidorException;
import br.com.vidal.projeto_mv_xpto.model.Cliente;
import br.com.vidal.projeto_mv_xpto.model.ContaBancaria;
import br.com.vidal.projeto_mv_xpto.model.Movimentacao;
import br.com.vidal.projeto_mv_xpto.repository.ClienteRepository;
import br.com.vidal.projeto_mv_xpto.repository.ContaBancariaRepository;
import br.com.vidal.projeto_mv_xpto.repository.MovimentacaoRepository;
import br.com.vidal.projeto_mv_xpto.shared.MovimentacaoDto;
import br.com.vidal.projeto_mv_xpto.view.model.RequestInstituicaoFinanceira;

@Service
public class MovimentacaoServiceImpl implements MovimentacaoService{
  @Autowired
  MovimentacaoRepository repository;
  
  @Autowired
  ClienteRepository clienteRepository;

  @Autowired
  ContaBancariaRepository contaBancariaRepository;

  @Override
  public List<MovimentacaoDto> retornaTodos() {
    try {
      List<Movimentacao> listaMovimentacao = repository.findAll();

      List<MovimentacaoDto> listaMovimentacaoDto = listaMovimentacao.stream()
                                                  .map(list -> new ModelMapper().map(list, MovimentacaoDto.class))
                                                  .collect(Collectors.toList());

      return listaMovimentacaoDto;

    } catch (Exception e) {
      throw new ErroServidorException();
    }    
  }

  @Override
  public Optional<MovimentacaoDto> retornaPorId(Integer id) {
    try {
      Optional<Movimentacao> movimentacao = repository.findById(id);

      if (movimentacao.isPresent()) {
        MovimentacaoDto movimentacaoDto = new ModelMapper().map(movimentacao.get(), MovimentacaoDto.class);
        return Optional.of(movimentacaoDto);
        
      } else {
        return Optional.empty();
      }

    } catch (Exception e) {
      throw new ErroServidorException();
    }
    
  }

  @Override
  public MovimentacaoDto gravarMovimentacao(RequestInstituicaoFinanceira request) {
    try {
      Optional<Cliente> cliente = clienteRepository.findByCpfCnpj(request.getCpfCnpj());
      Optional<ContaBancaria> contaBancaria = contaBancariaRepository.findByCodigoBancoAndNumeroContaAndAgencia(request.getCodigoBanco(), request.getNumeroConta(), request.getAgencia());

      if ((cliente.isPresent()) && (contaBancaria.isPresent())) {
        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setCliente(cliente.get());
        movimentacao.setContaBancaria(contaBancaria.get());
        movimentacao.setValor(request.getValor());
        movimentacao.setData(request.getDataMovimentacao());

        movimentacao = repository.save(movimentacao);

        return new ModelMapper().map(movimentacao, MovimentacaoDto.class);

      } else {
        return null;
      }
    } catch (Exception e) {
      throw new ErroServidorException();
    }
  }



  
}
