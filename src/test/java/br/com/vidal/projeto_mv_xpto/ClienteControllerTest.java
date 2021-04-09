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
import br.com.vidal.projeto_mv_xpto.shared.ClienteDto;
import br.com.vidal.projeto_mv_xpto.shared.ContaBancariaDto;
import br.com.vidal.projeto_mv_xpto.view.controller.ClienteController;

@WebMvcTest
public class ClienteControllerTest {
  @Autowired
  MockMvc mockMvc;

  @Autowired
  ClienteController clienteController;

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
  static class ClienteConfiguration{
    @Bean
    public ClienteDto buildCliente(){
      ClienteDto clienteDto = new ClienteDto();
      clienteDto.setIdCliente(1);
      clienteDto.setNome("Joao");
      clienteDto.setCpfCnpj("999.999.999-99");
      clienteDto.setEndereco("Rua teste Junit");
      clienteDto.setBairro("bairro java");
      clienteDto.setCidade("cidade spring");
      clienteDto.setNumero(999);
      clienteDto.setCep(1245678798);
      clienteDto.setComplemento("complemento teste");
      clienteDto.setTelefone("(99) 99999-9999");
      clienteDto.setSaldoInicial(1000.99);

      ContaBancariaDto contaBancaria = new ContaBancariaDto(1, "conta teste", "001", 123456, 1234);
      List<ContaBancariaDto> listaContaBancaria = List.of(contaBancaria);
      clienteDto.setContasBancarias(listaContaBancaria);     
      return clienteDto;
    }
  }

  @Autowired
  ClienteDto clienteDto;

  @BeforeEach
  public void setup(){
    this.mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
  }

  @Test
  public void deve_retornar_status_204_ao_mandar_solicitacao_get_com_lista_vazia() throws Exception{
    List<ClienteDto> listaClienteDto = new ArrayList<>();

    when(this.clienteService.retornaTodos()).thenReturn(listaClienteDto);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cliente"))
                                          .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  public void deve_retornar_status_200_ao_mandar_solicitacao_get_com_lista_preenchida() throws Exception{
    List<ClienteDto> listaClienteDto = new ArrayList<>();
    listaClienteDto.add(clienteDto);

    when(this.clienteService.retornaTodos()).thenReturn(listaClienteDto);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cliente"))
                                          .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  public void deve_retornar_200_cliente_joao_ao_mandar_solicitacao_get_com_id_1() throws Exception{
    
    when(clienteService.retornaPorid(1)).thenReturn(Optional.of(clienteDto));

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cliente/{id}", "1"))
                                          .andExpect(MockMvcResultMatchers.status().isOk())
                                          .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Joao"));
  }

  @Test
  public void deve_retornar_204_ao_mandar_solicitacao_get_com_id_1() throws Exception{

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cliente/{id}", "1"))
                                          .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  public void deve_retornar_201_criar_pessoa_com_id_1() throws Exception {     
    String json = new ObjectMapper().writeValueAsString(clienteDto);

    when(clienteService.salvarCliente(any())).thenReturn(clienteDto);

    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cliente")
                                          .content(json)
                                          .contentType(MediaType.APPLICATION_JSON))
                                          .andExpect(MockMvcResultMatchers.status().isCreated())
                                          .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Joao"));
  }

  @Test
  public void deve_retornar_200_excluir_cliente_com_id_1() throws Exception {

    when(clienteService.apagarCliente(1)).thenReturn("Cliente excluído com sucesso");

    mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/cliente/{id}", "1"))
          .andExpect(MockMvcResultMatchers.status().isOk());
  }

}
