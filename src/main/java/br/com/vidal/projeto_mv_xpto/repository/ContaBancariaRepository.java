package br.com.vidal.projeto_mv_xpto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.vidal.projeto_mv_xpto.model.ContaBancaria;

@Repository
public interface ContaBancariaRepository extends JpaRepository<ContaBancaria, Integer>{
  Optional<ContaBancaria> findByCodigoBancoAndNumeroContaAndAgencia(String codigoBanco, Integer numeroConta, Integer Agencia);
}
