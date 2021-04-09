package br.com.vidal.projeto_mv_xpto.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.vidal.projeto_mv_xpto.model.Movimentacao;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Integer>{
  @Query(value = "select * from XPTO.MOVIMENTACAO m where m.ID_CLIENTE = :idCliente",
        nativeQuery = true)
  List<Movimentacao> listaPorIdCliente(@Param("idCliente") Integer idCliente);

  @Query(value = "select * from XPTO.MOVIMENTACAO m where m.ID_CLIENTE = :idCliente and m.DATA BETWEEN TO_DATE(:dataInicial,'YYYY-MM-DD') and TO_DATE(:dataFinal,'YYYY-MM-DD')",
        nativeQuery = true)
  List<Movimentacao> listaPorIdClienteComFiltroPorPeriodo(@Param("idCliente") Integer idCliente, 
                                                          @Param("dataInicial") String dataInicial,
                                                          @Param("dataFinal") String dataFinalAjustadaConvertida);

  @Query(value = "select * from XPTO.MOVIMENTACAO m where m.ID_CONTA_BANCARIA = :idContaBancaria",
      	nativeQuery = true)
  List<Movimentacao> listaPorIdContaBancaria(@Param("idContaBancaria") Integer idContaBancaria);                                                          

  List<Movimentacao> findByDataBetween(LocalDate dataInicial, LocalDate dataFinal);

}
