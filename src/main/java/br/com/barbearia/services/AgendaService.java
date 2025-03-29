package br.com.barbearia.services;

import br.com.barbearia.exceptions.ObjectNotFoundException;
import br.com.barbearia.models.Agenda;
import br.com.barbearia.repository.AgendaRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AgendaService {

    @Autowired
    public AgendaRepository agendaRepository;

    // Verifica se já existe um agendamento para o barbeiro no mesmo horário
    public boolean existeAgendamentoComBarbeiroNoMesmoHorario(Long barbeiroId, LocalDateTime dataHora) {
        return agendaRepository.existsByBarbeiro_BarbeiroIdAndDataHora(barbeiroId, dataHora);
    }



    // Cria um novo agendamento após verificar conflitos de horário
    public Agenda save(Agenda agenda) {
        if (agenda.getBarbeiro() == null || agenda.getDataHora() == null || agenda.getCliente() == null) {
            throw new IllegalArgumentException("Preencha todos os campos para realizar o  agendamento ");
        }


        if (existeAgendamentoComBarbeiroNoMesmoHorario(agenda.getBarbeiro().getBarbeiroId(), agenda.getDataHora())) {
           throw new IllegalArgumentException("Já existe um agendamento para este barbeiro neste horário.");

        }
        return agendaRepository.save(agenda);

    }

    //busca todas as datas agendadas

    public @NotNull List<LocalDateTime> buscarPorTodasDatasAgendadas() {
        List<Agenda> agendamentos = agendaRepository.findAll();
        if (agendamentos.isEmpty()) {
            throw new ObjectNotFoundException("Nenhum agendamento encontrado");
        }

        return agendamentos.stream()
                .map(Agenda:: getDataHora)
                .collect(Collectors.toList());
    }

    public Agenda atualizarAgenda(Long agendaId, Agenda agenda) {
        // Busca o agendamento existente
        Agenda agendamentoAtualizado = agendaRepository.findById(agendaId)
                .orElseThrow(() -> new ObjectNotFoundException("Agendamento com ID " + agendaId + " não localizado."));

        // Verifica se a atualização mantém o mesmo horário ou se há um conflito
        if (!agendamentoAtualizado.getDataHora().equals(agenda.getDataHora()) &&
                existeAgendamentoComBarbeiroNoMesmoHorario(agenda.getBarbeiro().getBarbeiroId(), agenda.getDataHora())) {
            throw new IllegalArgumentException("O barbeiro já possui um agendamento no horário escolhido");
        }

        // Atualiza os dados do agendamento
        agendamentoAtualizado.setCliente(agenda.getCliente());
        agendamentoAtualizado.setBarbeiro(agenda.getBarbeiro());
        agendamentoAtualizado.setDataHora(agenda.getDataHora());

        // Salva e retorna o agendamento atualizado
        return agendaRepository.save(agendamentoAtualizado);
    }


    //Excluir um agendamento para um barbeiro em um horário específico

    public void excluirAgendamentoDoBarbeiro(Long barbeiroId, LocalDateTime dataHora) {
        Optional<Agenda> agendamentoExistente = agendaRepository.findAll()
                .stream()
                .filter(agenda -> agenda.getBarbeiro().getBarbeiroId().equals(barbeiroId)
                        && agenda.getDataHora().equals(dataHora))
                .findFirst();

        if (agendamentoExistente.isPresent()) {
            agendaRepository.delete(agendamentoExistente.get());
        } else {
            throw new ObjectNotFoundException("Agendamento não encontrado para o barbeiro no horário especificado");
        }
    }

    //excluir agendamento do cliente

    public void excluirAgendamentoDoCliente(Long clienteId, LocalDateTime dataHora) {
        Optional<Agenda> agendamentoExistente = agendaRepository.findAll()
                .stream()
                .filter(agenda -> agenda.getCliente().getClienteId().equals(clienteId)
                        && agenda.getDataHora().equals(dataHora))
                .findFirst();

        if (agendamentoExistente.isPresent()) {
            agendaRepository.delete(agendamentoExistente.get());
        } else {
            throw new ObjectNotFoundException("Agendamento não encontrado para o barbeiro no horário especificado");
        }
    }

    public List<Agenda> findAll(){
        return agendaRepository.findAll();
    }
}





