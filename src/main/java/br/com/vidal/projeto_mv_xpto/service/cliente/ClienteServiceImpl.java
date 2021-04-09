package br.com.vidal.projeto_mv_xpto.service.cliente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vidal.projeto_mv_xpto.exception.ErroServidorException;
import br.com.vidal.projeto_mv_xpto.model.Cliente;
import br.com.vidal.projeto_mv_xpto.repository.ClienteRepository;
import br.com.vidal.projeto_mv_xpto.service.contaBancaria.ContaBancariaService;
import br.com.vidal.projeto_mv_xpto.service.contaCliente.ContaClienteService;
import br.com.vidal.projeto_mv_xpto.shared.ClienteDto;
import br.com.vidal.projeto_mv_xpto.shared.ContaBancariaDto;

@Service
public class ClienteServiceImpl implements ClienteService {
  @Autowired
  ClienteRepository repository;

  @Autowired
  ContaClienteService contaClienteService;

  @Autowired
  ContaBancariaService contaBancariaService;

  @Override
  public List<ClienteDto> retornaTodos() {
    try {
      List<Cliente> listaCliente = repository.findAll();

      List<ClienteDto> listaClienteDto = listaCliente.stream()
                                        .map(dto -> new ModelMapper().map(dto, ClienteDto.class))
                                        .collect(Collectors.toList());

      return listaClienteDto; 

    } catch (Exception e) {
      throw new ErroServidorException();
    }     
  }

  @Override
  public Optional<ClienteDto> retornaPorid(Integer id) {
    try {
      Optional<Cliente> cliente = repository.findById(id);

      if (cliente.isPresent()) {
        ClienteDto clienteDto = new ModelMapper().map(cliente.get(), ClienteDto.class);
        return Optional.of(clienteDto);
  
      } else {
        return Optional.empty();
      }

    } catch (Exception e) {
      throw new ErroServidorException();
    }      
  }

  @Override
  public ClienteDto salvarCliente(ClienteDto request) {
    try {
      ModelMapper mapper = new ModelMapper();
      Cliente cliente = mapper.map(request, Cliente.class);

      if (cliente.getDataCadastro() == null) {
        cliente.setDataCadastro(LocalDate.now());
      }

      cliente = repository.save(cliente);

      Integer idCliente = cliente.getIdCliente();
      List<ContaBancariaDto> listaContaBancaria = new ArrayList<>();
      
      request.getContasBancarias().forEach(conta ->{
        contaClienteService.gravaContaCliente(idCliente, conta.getIdContaBancaria());

        Optional<ContaBancariaDto> contaBancariaDto = contaBancariaService.retornaPorId(conta.getIdContaBancaria());
        listaContaBancaria.add(contaBancariaDto.get());
      });

      ClienteDto clienteDto = mapper.map(cliente, ClienteDto.class);
      clienteDto.setContasBancarias(listaContaBancaria);

      return clienteDto; 
      
    } catch (Exception e) {
      throw new ErroServidorException();
    }       
  }

  @Override
  public ClienteDto alterarCliente(Integer id, ClienteDto clienteDto) {
    Optional<ClienteDto> clienteRetornadoDoBanco = retornaPorid(id);

    if (clienteRetornadoDoBanco.isPresent()) {
      ModelMapper mapper = new ModelMapper();
      Cliente cliente = mapper.map(clienteRetornadoDoBanco.get(), Cliente.class);

      if(clienteDto.getNome() != null){
        cliente.setNome(clienteDto.getNome()); 
      }

      if(clienteDto.getEndereco() != null){
        cliente.setEndereco(clienteDto.getEndereco()); 
      }

      if(clienteDto.getBairro() != null){
        cliente.setBairro(clienteDto.getBairro()); 
      }

      if(clienteDto.getCidade() != null){
        cliente.setCidade(clienteDto.getCidade()); 
      }

      if(clienteDto.getNumero() != null){
        cliente.setNumero(clienteDto.getNumero()); 
      }

      if(clienteDto.getCep() != null){
        cliente.setCep(clienteDto.getCep()); 
      }

      if(clienteDto.getComplemento() != null){
        cliente.setComplemento(clienteDto.getComplemento()); 
      }

      if(clienteDto.getTelefone() != null){
        cliente.setTelefone(clienteDto.getTelefone()); 
      }      

      try {
        cliente = repository.save(cliente);
        
      } catch (Exception e) {
        throw new ErroServidorException();
      }
      
      return mapper.map(cliente, ClienteDto.class);

    } else {
      return null;
    }
  }

  @Override
  public String apagarCliente(Integer id) {
    try {
      repository.deleteById(id);
      return "Cliente excluído com sucesso";

    } catch (Exception e) {
      throw new ErroServidorException();
    }
  }  


  
}
