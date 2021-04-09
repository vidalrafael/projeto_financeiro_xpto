package br.com.vidal.projeto_mv_xpto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.vidal.projeto_mv_xpto.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
  Optional<Cliente> findByCpfCnpj(String cpfCnpj);
}
