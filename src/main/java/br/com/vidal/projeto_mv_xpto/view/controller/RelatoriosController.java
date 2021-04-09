package br.com.vidal.projeto_mv_xpto.view.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vidal.projeto_mv_xpto.service.relatorios.RelatoriosService;
import br.com.vidal.projeto_mv_xpto.view.model.relatorios.RelatorioDeReceitaDaEmpresa;
import br.com.vidal.projeto_mv_xpto.view.model.relatorios.RelatorioDeSaldoDoCliente;
import br.com.vidal.projeto_mv_xpto.view.model.relatorios.RelatorioDeTodosOsClientes;

@RestController
@RequestMapping("api/v1/relatorio")
public class RelatoriosController {
  @Autowired
  RelatoriosService service;
  
  @GetMapping(path = "/relatorio_de_saldo_do_cliente/{idCliente}")
  public ResponseEntity<?> relatorioDeSaldoPorCliente(@PathVariable Integer idCliente) {
    RelatorioDeSaldoDoCliente relatorio = service.relatorioDeSaldoPorCliente(idCliente);

    if (relatorio != null) {
      return new ResponseEntity<>(relatorio, HttpStatus.OK);
      
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping(path = "/relatorio_de_saldo_do_cliente_por_periodo/{idCliente}/{dataInicial}/{dataFinal}")
  public ResponseEntity<?> relatorioDeSaldoPorClientePorPeriodo(@PathVariable Integer idCliente, @PathVariable String dataInicial, @PathVariable String dataFinal) {

    RelatorioDeSaldoDoCliente relatorio = service.relatorioDeSaldoPorClientePorPeriodo(idCliente, dataInicial, dataFinal);

    if (relatorio != null) {
      return new ResponseEntity<>(relatorio, HttpStatus.OK);
      
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping(path = "/relatorio_de_todos_os_clientes")
  public ResponseEntity<?> relatorioDeTodosOsClientes() {
    List<RelatorioDeTodosOsClientes> relatorio = service.relatorioDeTodosOsClientes();

    if (relatorio != null) {
      return new ResponseEntity<>(relatorio, HttpStatus.OK);
      
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping(path = "/relatorio_de_receitas_da_empresa/{dataInicial}/{dataFinal}")
  public ResponseEntity<?> relatorioDeReceitasDaEmpresa(@PathVariable String dataInicial, @PathVariable String dataFinal) {
    RelatorioDeReceitaDaEmpresa relatorio = service.relatorioDeReceitasDaEmpresa(dataInicial, dataFinal);

    if (relatorio != null) {
      return new ResponseEntity<>(relatorio, HttpStatus.OK);
      
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
  
}