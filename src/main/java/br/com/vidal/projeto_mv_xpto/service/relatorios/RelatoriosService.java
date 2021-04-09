package br.com.vidal.projeto_mv_xpto.service.relatorios;

import java.util.List;

import br.com.vidal.projeto_mv_xpto.view.model.relatorios.RelatorioDeReceitaDaEmpresa;
import br.com.vidal.projeto_mv_xpto.view.model.relatorios.RelatorioDeSaldoDoCliente;
import br.com.vidal.projeto_mv_xpto.view.model.relatorios.RelatorioDeTodosOsClientes;

public interface RelatoriosService {

  RelatorioDeSaldoDoCliente relatorioDeSaldoPorCliente(Integer idCliente);

  RelatorioDeSaldoDoCliente relatorioDeSaldoPorClientePorPeriodo(Integer idCliente, String dataInicial, String dataFinal);

  List<RelatorioDeTodosOsClientes> relatorioDeTodosOsClientes();

  RelatorioDeReceitaDaEmpresa relatorioDeReceitasDaEmpresa(String dataInicial, String dataFinal);
  
}
