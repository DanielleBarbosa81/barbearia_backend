package br.com.barbearia.services;

import br.com.barbearia.exceptions.ObjectNotFoundException;
import br.com.barbearia.models.Agenda;
import br.com.barbearia.models.Barbeiro;
import br.com.barbearia.models.Cliente;
import br.com.barbearia.repository.AgendaRepository;
import br.com.barbearia.repository.BarbeiroRepository;
import br.com.barbearia.repository.ClienteRepository;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BarbeiroRepository barbeiroRepository;

    @Autowired
    private ModelMapper modelMapper;


    // Verifica se já existe um agendamento para o barbeiro no mesmo horário
    public boolean existeAgendamentoComBarbeiroNoMesmoHorario(Long barbeiroId, LocalDateTime dataHora) {
        return agendaRepository.existsByBarbeiro_BarbeiroIdAndDataHora(barbeiroId, dataHora);
    }


    public Agenda save(Agenda agendaDto) {
        // Busca cliente e barbeiro no banco
        Cliente cliente = clienteRepository.findById(agendaDto.getCliente().getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
        Barbeiro barbeiro = barbeiroRepository.findById(agendaDto.getBarbeiro().getBarbeiroId())
                .orElseThrow(() -> new IllegalArgumentException("Barbeiro não encontrado"));

        // Verifica se já existe um agendamento para o barbeiro nesse horário
        if (agendaRepository.existsByBarbeiro_BarbeiroIdAndDataHora(barbeiro.getBarbeiroId(), agendaDto.getDataHora())) {
            throw new IllegalArgumentException("Já existe um agendamento para este barbeiro neste horário.");
        }

        // Converte DTO para entidade usando ModelMapper
        Agenda agenda = modelMapper.map(agendaDto, Agenda.class);

        // Define manualmente Cliente e Barbeiro (pois ModelMapper não sabe buscar no banco)
        agenda.setCliente(cliente);
        agenda.setBarbeiro(barbeiro);

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





