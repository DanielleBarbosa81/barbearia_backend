package br.com.barbearia.repository;

import br.com.barbearia.models.Barbeiro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BarbeiroRepository extends JpaRepository<Barbeiro, Long> {

    List<Barbeiro> findByBarbeiroNomeContainingIgnoreCase(String barbeiroNome);

    List<Barbeiro> findByBarbeiroEspecialidadeContainingIgnoreCase(String barbeiroEspecialidade);

    public Optional<Barbeiro> findByBarbeiroEspecialidade (String barbeiroEspecialidade);




}
