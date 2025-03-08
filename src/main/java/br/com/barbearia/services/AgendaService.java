package br.com.barbearia.services;

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
    private AgendaRepository agendaRepository;

    public boolean existeAgendamentoComBarbeiroNoMesmoHorario(Long barbeiroId, String dataHora) {
        // Verifica se já existe um agendamento com o mesmo barbeiro e a mesma dataHora
        List<Agenda> agendamentos = agendaRepository.findAll();
        return agendamentos.stream()
                .anyMatch(agenda -> agenda.getBarbeiro().getId().equals(barbeiroId) && agenda.getDataHora().equals(dataHora));

    }
    public Agenda criarAgenda(Agenda agenda) {
        // Verificar se já existe um agendamento para o mesmo barbeiro na mesma data
        if(existeAgendamentoComBarbeiroNoMesmoHorario(agenda.getBarbeiro().getId(), agenda.getDataHora())){
            throw new IllegalArgumentException("Já existe um agendamento para este barbeiro neste horario.");
        }
         return agendaRepository.save(agenda);
    }

    public List<String> buscarPorTodasDatasAgendadas(){
        List<Agenda> agendamentos = agendaRepository.findAll();
        return agendamentos.stream()
                .map(Agenda::getDataHora).collect(Collectors.toList());
    }

    public Agenda atualizarAgenda(Long id, Agenda agenda) {
        Optional<Agenda> agendamentoExistente = agendaRepository.findById(id);
        if (agendamentoExistente.isPresent()) {
            Agenda agendamento = agendamentoExistente.get();

            if (!agendamento.getDataHora().equals(agenda.getDataHora()) &&
                    existeAgendamentoComBarbeiroNoMesmoHorario(agenda.getBarbeiro().getId(), agenda.getDataHora())) {
                throw new IllegalArgumentException("O barbeiro já possui um agendamento no horário escolhido.");
            }

            agendamento.setCliente(agenda.getCliente());
            agendamento.setBarbeiro(agenda.getBarbeiro());
            agendamento.setDataHora(agenda.getDataHora());
            return agendaRepository.save(agendamento);
        }
        throw  new IllegalArgumentException("Agendamento não localizado!");
    }

    //excluir agendamento do barbeiro com horario especifico
    public void excluirAgendamentoDoBarbeiro(Long barbeiroId,String dataHora){
        Optional<Agenda> agendamentoExistente = agendaRepository.findAll().stream()
                .filter(agenda -> agenda.getBarbeiro().getId().equals(barbeiroId)
                        && agenda.getDataHora().equals(dataHora)).findFirst();

        if(agendamentoExistente.isPresent()){
            agendaRepository.delete(agendamentoExistente.get());
        }
        throw new IllegalArgumentException("Agendamento não encontrado para o barbeiro no horário especificado.");
    }

}
