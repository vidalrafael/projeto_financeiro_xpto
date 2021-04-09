package br.com.vidal.projeto_mv_xpto.view.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vidal.projeto_mv_xpto.service.movimentacao.MovimentacaoService;
import br.com.vidal.projeto_mv_xpto.shared.MovimentacaoDto;
import br.com.vidal.projeto_mv_xpto.view.model.RequestInstituicaoFinanceira;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("api/v1/movimentacao")
public class MovimentacaoController {
  @Autowired
  MovimentacaoService service;

  @GetMapping()
  public ResponseEntity<?> retornaTodos() {
    List<MovimentacaoDto> listaMovimentacaoDto = service.retornaTodos();

    if (listaMovimentacaoDto.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    } else {
      return new ResponseEntity<>(listaMovimentacaoDto, HttpStatus.OK);
    }
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<?> retornaPorId(@PathVariable Integer id) {
    Optional<MovimentacaoDto> movimentacaoDto = service.retornaPorId(id);

    if (movimentacaoDto.isPresent()) {
      return new ResponseEntity<>(movimentacaoDto, HttpStatus.OK);

    } else {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
  }

  @PostMapping()
  public ResponseEntity<?> gravarMovimentacao(@RequestBody @Valid RequestInstituicaoFinanceira request) {
    MovimentacaoDto movimentacaoDto = service.gravarMovimentacao(request);
      
    return new ResponseEntity<>(movimentacaoDto, HttpStatus.CREATED);
  }
  
  
  
}
