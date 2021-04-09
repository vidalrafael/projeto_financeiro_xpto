package br.com.vidal.projeto_mv_xpto;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.vidal.projeto_mv_xpto.model.Cliente;
import br.com.vidal.projeto_mv_xpto.model.ContaBancaria;
import br.com.vidal.projeto_mv_xpto.service.cliente.ClienteService;
import br.com.vidal.projeto_mv_xpto.service.contaBancaria.ContaBancariaService;
import br.com.vidal.projeto_mv_xpto.service.contaCliente.ContaClienteService;
import br.com.vidal.projeto_mv_xpto.service.movimentacao.MovimentacaoService;
import br.com.vidal.projeto_mv_xpto.service.relatorios.RelatoriosService;
import br.com.vidal.projeto_mv_xpto.shared.MovimentacaoDto;
import br.com.vidal.projeto_mv_xpto.view.controller.MovimentacaoController;

@WebMvcTest
public class MovimentacaoControllerTest {
  @Autowired
  MockMvc mockMvc;

  @Autowired
  MovimentacaoController movimentacaoController;

  @MockBean
  ClienteService clienteService;

  @MockBean
  ContaBancariaService contaBancariaService;

  @MockBean
  ContaClienteService contaClienteService;

  @MockBean
  MovimentacaoService movimentacaoService;

  @MockBean
  RelatoriosService relatoriosService;

  @TestConfiguration
  static class ContaBancariaConfiguration{
    @Bean
    public MovimentacaoDto buildMovimentacao(){
      MovimentacaoDto movimentacaoDto = new MovimentacaoDto();

      Cliente cliente = new Cliente();
      cliente.setIdCliente(1);
      cliente.setNome("Joao");
      cliente.setCpfCnpj("999.999.999-99");
      cliente.setEndereco("Rua teste Junit");
      cliente.setBairro("bairro java");
      cliente.setCidade("cidade spring");
      cliente.setNumero(999);
      cliente.setCep(1245678798);
      cliente.setComplemento("complemento teste");
      cliente.setTelefone("(99) 99999-9999");
      cliente.setDataCadastro(LocalDate.parse("2021-01-01"));
      cliente.setSaldoInicial(1000.00);

      ContaBancaria contaBancaria = new ContaBancaria();
      contaBancaria.setIdContaBancaria(1);
      contaBancaria.setNome("Conta Bradesco");
      contaBancaria.setCodigoBanco("237");
      contaBancaria.setAgencia(1234);
      contaBancaria.setNumeroConta(123456);

      movimentacaoDto.setIdMovimentacao(1);
      movimentacaoDto.setCliente(cliente);
      movimentacaoDto.setContaBancaria(contaBancaria);
      movimentacaoDto.setData(LocalDate.parse("2021-01-01"));
      movimentacaoDto.setValor(1000.00);

      return movimentacaoDto;      
    }
  }

  @Autowired
  MovimentacaoDto movimentacaoDto;

  @BeforeEach
  public void setup(){
    this.mockMvc = MockMvcBuilders.standaloneSetup(movimentacaoController).build();
  }

  @Test
  public void deve_retornar_status_204_ao_mandar_solicitacao_get_com_lista_vazia() throws Exception{
    List<MovimentacaoDto> listaMovimentacaoDto = new ArrayList<>();

    when(this.movimentacaoService.retornaTodos()).thenReturn(listaMovimentacaoDto);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movimentacao"))
                                          .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  public void deve_retornar_status_200_ao_mandar_solicitacao_get_com_lista_preenchida() throws Exception{
    List<MovimentacaoDto> listaMovimentacaoDto = new ArrayList<>();
    listaMovimentacaoDto.add(movimentacaoDto);

    when(this.movimentacaoService.retornaTodos()).thenReturn(listaMovimentacaoDto);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movimentacao"))
                                          .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  public void deve_retornar_200_valor_1000_ao_mandar_solicitacao_get_com_id_1() throws Exception{
    
    when(movimentacaoService.retornaPorId(1)).thenReturn(Optional.of(movimentacaoDto));

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movimentacao/{id}", "1"))
                                          .andExpect(MockMvcResultMatchers.status().isOk())
                                          .andExpect(MockMvcResultMatchers.jsonPath("$.valor").value(1000));
  }

  @Test
  public void deve_retornar_204_ao_mandar_solicitacao_get_com_id_1() throws Exception{

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/movimentacao/{id}", "1"))
                                          .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

}
