package br.com.barbearia.repository;

import br.com.barbearia.models.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDateTime;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {


    boolean existsByBarbeiro_BarbeiroIdAndDataHora(Long barbeiroId, LocalDateTime dataHora);

    boolean existsByCliente_ClienteId(Long clienteId);
}

