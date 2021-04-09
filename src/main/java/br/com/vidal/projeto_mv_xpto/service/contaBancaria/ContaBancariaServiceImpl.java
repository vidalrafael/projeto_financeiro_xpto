package br.com.vidal.projeto_mv_xpto.service.contaBancaria;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vidal.projeto_mv_xpto.exception.ErroServidorException;
import br.com.vidal.projeto_mv_xpto.model.ContaBancaria;
import br.com.vidal.projeto_mv_xpto.model.Movimentacao;
import br.com.vidal.projeto_mv_xpto.repository.ContaBancariaRepository;
import br.com.vidal.projeto_mv_xpto.repository.MovimentacaoRepository;
import br.com.vidal.projeto_mv_xpto.shared.ContaBancariaDto;

@Service
public class ContaBancariaServiceImpl implements ContaBancariaService {
  @Autowired
  ContaBancariaRepository repository;

  @Autowired
  MovimentacaoRepository movimentacaoRepository;

  @Override
  public List<ContaBancariaDto> retornaTodos() {
    try {
      List<ContaBancaria> listaContaBancaria = repository.findAll();

      List<ContaBancariaDto> listaContaBancariaDto = listaContaBancaria.stream()
                                                  .map(conta -> new ModelMapper().map(conta, ContaBancariaDto.class))
                                                  .collect(Collectors.toList());
      return listaContaBancariaDto;

    } catch (Exception e) {
      throw new ErroServidorException();
    }                                                      
  }

  @Override
  public Optional<ContaBancariaDto> retornaPorId(Integer id) {
    try {
      Optional<ContaBancaria> contaBancaria = repository.findById(id);

      if (contaBancaria.isPresent()) {
        ContaBancariaDto contaBancariaDto = new ModelMapper().map(contaBancaria.get(), ContaBancariaDto.class);
        return Optional.of(contaBancariaDto);

      } else {
        return Optional.empty();
      }

    } catch (Exception e) {
      throw new ErroServidorException();
    }    
  }

  @Override
  public ContaBancariaDto gravarContaBancaria(ContaBancariaDto request) {
    ModelMapper mapper = new ModelMapper();
    ContaBancaria contaBancaria = mapper.map(request, ContaBancaria.class);

    try {
      contaBancaria = repository.save(contaBancaria);

      ContaBancariaDto contaBancariaDto = mapper.map(contaBancaria, ContaBancariaDto.class);
      return contaBancariaDto;
      
    } catch (Exception e) {
      throw new ErroServidorException();
    }    
  }

  @Override
  public ContaBancariaDto atualizarContaBancaria(Integer id, ContaBancariaDto request) {
    Optional<ContaBancariaDto> contaBancariaRetornadoDoBanco = retornaPorId(id);

    if (contaBancariaRetornadoDoBanco.isPresent()) {
      ModelMapper mapper = new ModelMapper();
      ContaBancaria contaBancaria = mapper.map(contaBancariaRetornadoDoBanco.get(), ContaBancaria.class);

      if (request.getNome() != null) {
        contaBancaria.setNome(request.getNome());
      }

      if (request.getCodigoBanco() != null) {
        contaBancaria.setCodigoBanco(request.getCodigoBanco());
      }

      if (request.getNumeroConta() != null) {
        contaBancaria.setNumeroConta(request.getNumeroConta());
      }

      if (request.getAgencia() != null) {
        contaBancaria.setAgencia(request.getAgencia());
      }

      try {
        contaBancaria = repository.save(contaBancaria);

      } catch (Exception e) {
        throw new ErroServidorException();
      }

      return mapper.map(contaBancaria, ContaBancariaDto.class);

    } else {
      return null;
    }
  }

  @Override
  public String apagarContaBancaria(Integer id) {
    List<Movimentacao> listaMovimentacao = movimentacaoRepository.listaPorIdContaBancaria(id);

    if (listaMovimentacao.size() == 0) {
      try {
        repository.deleteById(id);
        return "Conta excluída com sucesso";
  
      } catch (Exception e) {
        throw new ErroServidorException();
      }

    } else {      
      return "A conta não pode ser excluída, pois existe movimentação para ela.";
    }  
  }

}
