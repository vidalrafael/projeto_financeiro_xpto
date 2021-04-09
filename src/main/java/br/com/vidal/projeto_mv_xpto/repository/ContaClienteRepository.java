package br.com.vidal.projeto_mv_xpto.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.vidal.projeto_mv_xpto.model.ContaCliente;

@Repository
public interface ContaClienteRepository extends JpaRepository<ContaCliente, Integer> {
  @Query(value = "select * from XPTO.CONTA_CLIENTE c where c.ID_CLIENTE = :idCliente", 
        nativeQuery = true)
  List<ContaCliente> findByIdCliente(@Param("idCliente") Integer idCliente);
  
  @Modifying
  @Transactional
  @Query(value = "delete XPTO.CONTA_CLIENTE c where c.ID_CLIENTE = :idCliente and c.ID_CONTA_BANCARIA = :idContaBancaria",
        nativeQuery = true)
  void apagarRegistro(@Param("idCliente") Integer idCliente, @Param("idContaBancaria") Integer idContaBancaria);
}
