package br.com.barbearia.repository;

import br.com.barbearia.models.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    public abstract boolean existsByBarbeiroBarbeiroId(Long Id);
    boolean existsByClienteClienteId (Long Id);
}
