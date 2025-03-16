package br.com.barbearia.services;

import br.com.barbearia.exceptions.ObjectNotFoundException;
import br.com.barbearia.models.Agenda;
import br.com.barbearia.repository.AgendaRepository;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AgendaService {

    @Autowired
    public AgendaRepository agendaRepository;

    //verifica se já existe um agendamento para o barbeiro no mesmo horario

    public boolean existeAgendamentoComBarbeiroNoMesmoHorario(Long barbeiroId, String dataHora) {
        return agendaRepository.findAll().stream()
                .anyMatch(agenda -> agenda.getBarbeiro().getBarbeiroId().equals(barbeiroId) && agenda.getDataHora().equals(dataHora));
    }

    //cria um novo agendamento após verificar conflitos de horario

    public Agenda save(Agenda agenda) {
        if (existeAgendamentoComBarbeiroNoMesmoHorario(agenda.getBarbeiro().getBarbeiroId(), agenda.getDataHora())) {
            throw new IllegalArgumentException("Já existe um agendamento para este barbeiro neste horário");

        }
        return agendaRepository.save(agenda);
    }

    //busca todas as datas agendadas

    public List<String> buscarPorTodasDatasAgendadas() {
        List<Agenda> agendamentos = agendaRepository.findAll();
        if (agendamentos.isEmpty()) {
            throw new ObjectNotFoundException("Nenhum agendamento encontrado");
        }

        return agendamentos.stream()
                .map(Agenda::getDataHora)
                .collect(Collectors.toList());
    }

    // atualiza todos os agendamentos existentes apos verificar conflitos de horario

    public Agenda atualizarAgenda(Long agendaId, Agenda agenda) {
        Agenda agendamentoAtualizado = agendaRepository.findById(agendaId)
                .orElseThrow(() -> new ObjectNotFoundException("Agendamento com ID " + agendaId + " não localizado."));

        //verifica se o horario esta em conflito com outro agendamento
        if (!agendamentoAtualizado.getDataHora().equals(agenda.getDataHora())
                && existeAgendamentoComBarbeiroNoMesmoHorario(agenda.getBarbeiro().getBarbeiroId(), agenda.getDataHora())) {
            throw new IllegalArgumentException("O barbeiro já possui um agendamento no horário escolhido");
        }
        agendamentoAtualizado.setCliente(agenda.getCliente());
        agendamentoAtualizado.setBarbeiro(agenda.getBarbeiro());
        agendamentoAtualizado.setDataHora(agenda.getDataHora());
        return agendaRepository.save(agendamentoAtualizado);
    }

    //Excluir um agendamento para um barbeiro em um horário específico

    public void excluirAgendamentoDoBarbeiro(Long barbeiroId, String dataHora) {
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

    public void excluirAgendamentoDoCliente(Long clienteId, String dataHora) {
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
}





