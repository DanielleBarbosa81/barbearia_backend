package br.com.barbearia.repository;

import br.com.barbearia.models.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {


    boolean existsByBarbeiro_BarbeiroIdAndDataHora(Long barbeiroId, LocalDateTime dataHora);

    boolean existsByCliente_ClienteId(Long clienteId);
}

