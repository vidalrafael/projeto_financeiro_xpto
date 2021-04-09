package br.com.vidal.projeto_mv_xpto.view.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vidal.projeto_mv_xpto.service.cliente.ClienteService;
import br.com.vidal.projeto_mv_xpto.shared.ClienteDto;
import br.com.vidal.projeto_mv_xpto.view.model.RequestAtualizaCliente;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/v1/cliente")
public class ClienteController {
  @Autowired
  ClienteService service;

  @GetMapping
  public ResponseEntity<?> retornaTodos(){
    List<ClienteDto> listaClienteDto = service.retornaTodos();

    if (listaClienteDto.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    } else {
      return new ResponseEntity<>(listaClienteDto, HttpStatus.OK);
    }
       
  }

  @GetMapping(path="/{id}")
  public ResponseEntity<?> retornaPorId(@PathVariable Integer id) {
    Optional<ClienteDto> clienteDto = service.retornaPorid(id);

    if (clienteDto.isPresent()) {
      return new ResponseEntity<>(clienteDto.get(), HttpStatus.OK);

    } else {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
  }  

  @PostMapping
  public ResponseEntity<?> gravarCliente(@RequestBody @Valid ClienteDto request) {
    ClienteDto clienteDto = service.salvarCliente(request);
    
    return new ResponseEntity<>(clienteDto, HttpStatus.CREATED);      
  }

  @PutMapping(path = "/{id}")
  public ResponseEntity<?> alterarCliente(@PathVariable Integer id, @RequestBody RequestAtualizaCliente request){    
    ClienteDto clienteDto = new ModelMapper().map(request, ClienteDto.class);
    clienteDto = service.alterarCliente(id, clienteDto);

    if (clienteDto != null) {
      return new ResponseEntity<>(clienteDto, HttpStatus.OK);

    } else {
      return new ResponseEntity<>(clienteDto, HttpStatus.NOT_MODIFIED);
    }    
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<?> apagarCliente(@PathVariable Integer id){
    return new ResponseEntity<>(service.apagarCliente(id), HttpStatus.OK);
  }
}
