package br.com.vidal.projeto_mv_xpto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.vidal.projeto_mv_xpto.service.cliente.ClienteService;
import br.com.vidal.projeto_mv_xpto.service.contaBancaria.ContaBancariaService;
import br.com.vidal.projeto_mv_xpto.service.contaCliente.ContaClienteService;
import br.com.vidal.projeto_mv_xpto.service.movimentacao.MovimentacaoService;
import br.com.vidal.projeto_mv_xpto.service.relatorios.RelatoriosService;
import br.com.vidal.projeto_mv_xpto.shared.ContaBancariaDto;
import br.com.vidal.projeto_mv_xpto.view.controller.ContaBancariaController;

@WebMvcTest
public class ContaBancariaControllerTest {
  @Autowired
  MockMvc mockMvc;

  @Autowired
  ContaBancariaController contaBancariaController;

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
    public ContaBancariaDto buildContaBancaria(){
      ContaBancariaDto contaBancariaDto = new ContaBancariaDto();
      contaBancariaDto.setIdContaBancaria(1);
      contaBancariaDto.setNome("Conta Bradesco");
      contaBancariaDto.setCodigoBanco("237");
      contaBancariaDto.setAgencia(1234);
      contaBancariaDto.setNumeroConta(123456);
      return contaBancariaDto;
    }
  }

  @Autowired
  ContaBancariaDto contaBancariaDto;

  @BeforeEach
  public void setup(){
    this.mockMvc = MockMvcBuilders.standaloneSetup(contaBancariaController).build();
  }

  @Test
  public void deve_retornar_status_204_ao_mandar_solicitacao_get_com_lista_vazia() throws Exception{
    List<ContaBancariaDto> listaContaBancariaDto = new ArrayList<>();

    when(this.contaBancariaService.retornaTodos()).thenReturn(listaContaBancariaDto);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/conta-bancaria"))
                                          .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  public void deve_retornar_status_200_ao_mandar_solicitacao_get_com_lista_preenchida() throws Exception{
    List<ContaBancariaDto> listaContaBancariaDto = new ArrayList<>();
    listaContaBancariaDto.add(contaBancariaDto);

    when(this.contaBancariaService.retornaTodos()).thenReturn(listaContaBancariaDto);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/conta-bancaria"))
                                          .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  public void deve_retornar_200_contaBradesco_ao_mandar_solicitacao_get_com_id_1() throws Exception{
    
    when(contaBancariaService.retornaPorId(1)).thenReturn(Optional.of(contaBancariaDto));

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/conta-bancaria/{id}", "1"))
                                          .andExpect(MockMvcResultMatchers.status().isOk())
                                          .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Conta Bradesco"));
  }

  @Test
  public void deve_retornar_204_ao_mandar_solicitacao_get_com_id_1() throws Exception{

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/conta-bancaria/{id}", "1"))
                                          .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  public void deve_retornar_201_criar_conta_bancaria_com_id_1() throws Exception {     
    String json = new ObjectMapper().writeValueAsString(contaBancariaDto);

    when(contaBancariaService.gravarContaBancaria(any())).thenReturn(contaBancariaDto);

    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/conta-bancaria")
                                          .content(json)
                                          .contentType(MediaType.APPLICATION_JSON))
                                          .andExpect(MockMvcResultMatchers.status().isCreated())
                                          .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Conta Bradesco"));
  }

  @Test
  public void deve_retornar_200_excluir_conta_bancaria_com_id_1() throws Exception {

    when(contaBancariaService.apagarContaBancaria(1)).thenReturn("Conta excluída com sucesso");

    mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/conta-bancaria/{id}", "1"))
          .andExpect(MockMvcResultMatchers.status().isOk());
  }

}

