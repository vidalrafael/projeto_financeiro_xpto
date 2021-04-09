package br.com.vidal.projeto_mv_xpto.view.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vidal.projeto_mv_xpto.service.contaBancaria.ContaBancariaService;
import br.com.vidal.projeto_mv_xpto.shared.ContaBancariaDto;

@RestController
@RequestMapping("api/v1/conta-bancaria")
public class ContaBancariaController {
  @Autowired 
  ContaBancariaService service;

  @GetMapping()
  public ResponseEntity<?> retornaTodos() {
    List<ContaBancariaDto> listaContaBancariaDto = service.retornaTodos();

    if (listaContaBancariaDto.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    } else {
      return new ResponseEntity<>(listaContaBancariaDto, HttpStatus.OK);
    }
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<?> retornaPorId(@PathVariable Integer id) {
    Optional<ContaBancariaDto> contaBancariaDto = service.retornaPorId(id);

    if (contaBancariaDto.isPresent()) {
      return new ResponseEntity<>(contaBancariaDto, HttpStatus.OK);

    } else {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
  }

  @PostMapping()
  public ResponseEntity<?> gravarContaBancaria(@RequestBody @Valid ContaBancariaDto request) {
    ContaBancariaDto contaBancariaDto = service.gravarContaBancaria(request);
      
    return new ResponseEntity<>(contaBancariaDto, HttpStatus.CREATED);

  }

  @PutMapping(path = "/{id}")
  public ResponseEntity<?> atualizarContaBancaria(@PathVariable Integer id, @RequestBody ContaBancariaDto request) {
    ContaBancariaDto contaBancariaDto = service.atualizarContaBancaria(id, request);

    if (contaBancariaDto != null) {
      return new ResponseEntity<>(contaBancariaDto, HttpStatus.OK);

    } else {
      return new ResponseEntity<>(request, HttpStatus.NOT_MODIFIED);
    }
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<?> apagarContaBancaria(@PathVariable Integer id) {    
    return new ResponseEntity<>(service.apagarContaBancaria(id), HttpStatus.OK); 
  }
  
}
