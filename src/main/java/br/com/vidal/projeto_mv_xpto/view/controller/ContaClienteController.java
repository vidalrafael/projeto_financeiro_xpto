package br.com.vidal.projeto_mv_xpto.view.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vidal.projeto_mv_xpto.service.contaCliente.ContaClienteService;
import br.com.vidal.projeto_mv_xpto.shared.ContaClienteDto;
import br.com.vidal.projeto_mv_xpto.view.model.RequestContaCliente;



@RestController
@RequestMapping("api/v1/conta-cliente")
public class ContaClienteController {
  @Autowired
  ContaClienteService service;

  @GetMapping(path = "/{idCliente}")
  public ResponseEntity<?> retornaContasPorCliente(@PathVariable Integer idCliente) {
    List<ContaClienteDto> listaContaClienteDto = service.retornaContasPorCliente(idCliente);

    if (listaContaClienteDto.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    } else {
      return new ResponseEntity<>(listaContaClienteDto, HttpStatus.OK);
    }
    
    
  }

  @DeleteMapping()
  public ResponseEntity<?> apagarContaCliente(@RequestBody RequestContaCliente request) {    
    return new ResponseEntity<>(service.apagarContaCliente(request.getIdCliente(), request.getIdContaBancaria()), HttpStatus.OK);      
  }

  
  
}
