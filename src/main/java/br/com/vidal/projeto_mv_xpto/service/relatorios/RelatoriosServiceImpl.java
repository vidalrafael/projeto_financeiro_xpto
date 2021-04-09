package br.com.vidal.projeto_mv_xpto.service.relatorios;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vidal.projeto_mv_xpto.model.Cliente;
import br.com.vidal.projeto_mv_xpto.model.Movimentacao;
import br.com.vidal.projeto_mv_xpto.repository.ClienteRepository;
import br.com.vidal.projeto_mv_xpto.repository.MovimentacaoRepository;
import br.com.vidal.projeto_mv_xpto.shared.ClienteDto;
import br.com.vidal.projeto_mv_xpto.view.model.relatorios.RelatorioDeReceitaDaEmpresa;
import br.com.vidal.projeto_mv_xpto.view.model.relatorios.RelatorioDeReceitaDaEmpresaAuxiliar;
import br.com.vidal.projeto_mv_xpto.view.model.relatorios.RelatorioDeSaldoDoCliente;
import br.com.vidal.projeto_mv_xpto.view.model.relatorios.RelatorioDeTodosOsClientes;
import br.com.vidal.projeto_mv_xpto.view.model.relatorios.ValorPorQuantidadeMovimentacao;

@Service
public class RelatoriosServiceImpl extends ValorPorQuantidadeMovimentacao implements RelatoriosService {
  @Autowired
  ClienteRepository clienteRepository;

  @Autowired
  MovimentacaoRepository movimentacaoRepository;

  @Override
  public RelatorioDeSaldoDoCliente relatorioDeSaldoPorCliente(Integer idCliente) {
    Optional<Cliente> cliente = clienteRepository.findById(idCliente);
    List<Movimentacao> listaMovimentacao = movimentacaoRepository.listaPorIdCliente(idCliente);

    if ((cliente.isPresent()) && (listaMovimentacao.size() != 0)) {
      String dataInicial = cliente.get().getDataCadastro().format(DateTimeFormatter.ISO_LOCAL_DATE);
      String dataFinal = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);

      return montarRelatorioPorCliente(cliente.get(), listaMovimentacao, dataInicial, dataFinal);
      
    } else {
      return null;
    }
  }

  @Override
  public RelatorioDeSaldoDoCliente relatorioDeSaldoPorClientePorPeriodo(Integer idCliente, String dataInicial, String dataFinal) {
    Optional<Cliente> cliente = clienteRepository.findById(idCliente);

    LocalDate dataFinalAjustada = LocalDate.parse(dataFinal);
    dataFinalAjustada = dataFinalAjustada.plusDays(1);
    String dataFinalAjustadaConvertida = dataFinalAjustada.format(DateTimeFormatter.ISO_LOCAL_DATE);

    List<Movimentacao> listaMovimentacao = movimentacaoRepository.listaPorIdClienteComFiltroPorPeriodo(idCliente, dataInicial, dataFinalAjustadaConvertida);

    if ((cliente.isPresent()) && (listaMovimentacao.size() != 0)) {
      return montarRelatorioPorCliente(cliente.get(), listaMovimentacao, dataInicial, dataFinal);
      
    } else {
      return null;
    }
  }

  private RelatorioDeSaldoDoCliente montarRelatorioPorCliente(Cliente cliente, List<Movimentacao> listaMovimentacao, String dataInicial, String dataFinal){
    RelatorioDeSaldoDoCliente relatorio = new RelatorioDeSaldoDoCliente();
    
    relatorio.setDataInicial(LocalDate.parse(dataInicial));
    relatorio.setDataFinal(LocalDate.parse(dataFinal));
    
    relatorio.setCliente(new ModelMapper().map(cliente, ClienteDto.class));

    listaMovimentacao.stream().forEach(movimento -> {
      if (movimento.getValor() > 0) {
        relatorio.setMovimentacaoCredito(relatorio.getMovimentacaoCredito() + movimento.getValor());

      } else {
        relatorio.setMovimentacaoDebito(relatorio.getMovimentacaoDebito() + movimento.getValor());
      }        
    });

    relatorio.setTotalMovimentacao(relatorio.getMovimentacaoCredito() + relatorio.getMovimentacaoDebito());

    relatorio.setQuantidadeMovimentacoes(listaMovimentacao.size());
    
    Double valorPorMovimentacao = valorPorQuantidademovimentacao(relatorio.getQuantidadeMovimentacoes());

    relatorio.setValorCobradoPelasMovimentacoes(valorPorMovimentacao);

    relatorio.setValorTotalPagoPelasMovimentacoes(relatorio.getQuantidadeMovimentacoes() * valorPorMovimentacao);

    relatorio.setSaldoAtual((cliente.getSaldoInicial() + relatorio.getTotalMovimentacao()) - relatorio.getValorTotalPagoPelasMovimentacoes());

    return relatorio;
  }

  @Override
  public List<RelatorioDeTodosOsClientes> relatorioDeTodosOsClientes() {
    List<Movimentacao> listaMovimentacao = movimentacaoRepository.findAll();
    List<RelatorioDeTodosOsClientes> listaRelatorioTodosOsClientes = new ArrayList<>();

    Map<Cliente, List<Movimentacao>> movimentoAgrupadoPorCliente = listaMovimentacao
                                                                  .stream()
                                                                  .collect(Collectors.groupingBy(Movimentacao::getCliente));

    movimentoAgrupadoPorCliente.entrySet().forEach(movimento ->{      
      RelatorioDeTodosOsClientes relatorio = new RelatorioDeTodosOsClientes();

      relatorio.setNome(movimento.getKey().getNome());
      relatorio.setDataCadastro(movimento.getKey().getDataCadastro());
      
      relatorio.setSaldo(movimento.getValue().stream()
                                             .collect(Collectors.summingDouble(Movimentacao::getValor)));

      listaRelatorioTodosOsClientes.add(relatorio);
    });

    return listaRelatorioTodosOsClientes;
  }

  @Override
  public RelatorioDeReceitaDaEmpresa relatorioDeReceitasDaEmpresa(String dataInicial, String dataFinal) { 
    RelatorioDeReceitaDaEmpresa relatorio = new RelatorioDeReceitaDaEmpresa();
    List<RelatorioDeReceitaDaEmpresaAuxiliar> listaRelatorioDeReceitaDaEmpresaAuxiliar = new ArrayList<>();
    
    LocalDate dataInicialConvertida = LocalDate.parse(dataInicial);
    LocalDate dataFinalConvertida = LocalDate.parse(dataFinal);   

    List<Movimentacao> listaMovimentacao = movimentacaoRepository.findByDataBetween(dataInicialConvertida, dataFinalConvertida);  

    Map<Cliente, List<Movimentacao>> movimentoAgrupadoPorCliente = listaMovimentacao
                                                                  .stream()
                                                                  .collect(Collectors.groupingBy(Movimentacao::getCliente));                                                                

    movimentoAgrupadoPorCliente.entrySet().forEach(movimento ->{
      RelatorioDeReceitaDaEmpresaAuxiliar auxiliar = new RelatorioDeReceitaDaEmpresaAuxiliar();  
      
      auxiliar.setNome(movimento.getKey().getNome());      
      auxiliar.setQuantidadeMovimentacoes(movimento.getValue().size());
      auxiliar.setValorMovimentacoes(auxiliar.getQuantidadeMovimentacoes() * valorPorQuantidademovimentacao(auxiliar.getQuantidadeMovimentacoes()));

      listaRelatorioDeReceitaDaEmpresaAuxiliar.add(auxiliar);

      relatorio.setTotalReceita(relatorio.getTotalReceita() + auxiliar.getValorMovimentacoes());
    });            
    
    relatorio.setDataInicial(dataInicialConvertida);
    relatorio.setDataFinal(dataFinalConvertida);  
    relatorio.setCliente(listaRelatorioDeReceitaDaEmpresaAuxiliar);

    return relatorio;
  }  

  
  
}
