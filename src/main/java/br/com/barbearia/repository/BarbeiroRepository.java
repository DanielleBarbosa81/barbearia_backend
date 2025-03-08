package br.com.barbearia.repository;

import br.com.barbearia.models.Barbeiro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BarbeiroRepository extends JpaRepository<Barbeiro, Long> {

    List<Barbeiro> findByNomeContainingIgnoreCase(String nome);

    List<Barbeiro> findByEspecialidadeContainingIgnoreCase(String especialidade);


}
