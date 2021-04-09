package br.com.vidal.projeto_mv_xpto.service.contaCliente;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vidal.projeto_mv_xpto.exception.ErroServidorException;
import br.com.vidal.projeto_mv_xpto.model.Cliente;
import br.com.vidal.projeto_mv_xpto.model.ContaBancaria;
import br.com.vidal.projeto_mv_xpto.model.ContaCliente;
import br.com.vidal.projeto_mv_xpto.model.ContaClienteCast;
import br.com.vidal.projeto_mv_xpto.repository.ClienteRepository;
import br.com.vidal.projeto_mv_xpto.repository.ContaBancariaRepository;
import br.com.vidal.projeto_mv_xpto.repository.ContaClienteRepository;
import br.com.vidal.projeto_mv_xpto.shared.ContaClienteDto;

@Service
public class ContaClienteServiceImpl implements ContaClienteService{
  @Autowired
  ContaClienteRepository repository;

  @Autowired
  ClienteRepository clienteRepository;

  @Autowired
  ContaBancariaRepository contaBancariaRepository;

  @Override
  public List<ContaClienteDto> retornaContasPorCliente(Integer idCliente) {
    try {
      List<ContaCliente> listaContaCliente = repository.findByIdCliente(idCliente);

      List<ContaClienteDto> listaContaClienteDto = listaContaCliente.stream()
                                                                    .map(list -> new ModelMapper().map(list, ContaClienteDto.class))
                                                                    .collect(Collectors.toList());

      return listaContaClienteDto; 

    } catch (Exception e) {
      throw new ErroServidorException();
    }
  }

  @Override
  public ContaClienteDto gravaContaCliente(Integer idCliente, Integer idContaBancaria) {
    try {
      Optional<Cliente> cliente = clienteRepository.findById(idCliente);
      Optional<ContaBancaria> contaBancaria = contaBancariaRepository.findById(idContaBancaria);

      if ((cliente.isPresent()) && (contaBancaria.isPresent())) {
        ModelMapper mapper = new ModelMapper();

        ContaClienteCast contaClienteCast = new ContaClienteCast(cliente.get(), contaBancaria.get());
        ContaClienteDto contaClienteDto = new ContaClienteDto(contaClienteCast);

        ContaCliente contaCliente = mapper.map(contaClienteDto, ContaCliente.class);
        contaCliente = repository.save(contaCliente);

        return mapper.map(contaCliente, ContaClienteDto.class);
       
      } else {
        return null;
      }

    } catch (Exception e) {
      throw new ErroServidorException();
    }
    
  }

  @Override
  public String apagarContaCliente(Integer idCliente, Integer idContaBancaria) {
    try {
      repository.apagarRegistro(idCliente, idContaBancaria);    
      return "O vínculo da conta bancaria foi removido com sucesso";

    } catch (Exception e) {
      throw new ErroServidorException();     
    }
  }

  
  
}
