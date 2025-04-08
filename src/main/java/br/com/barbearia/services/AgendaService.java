package br.com.barbearia.services;

import br.com.barbearia.models.Agenda;
import br.com.barbearia.models.Barbeiro;
import br.com.barbearia.models.Cliente;
import br.com.barbearia.repository.AgendaRepository;
import br.com.barbearia.repository.BarbeiroRepository;
import br.com.barbearia.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaService {

        @Autowired
        private AgendaRepository agendaRepository;

        @Autowired
        private ClienteRepository clienteRepository;

        @Autowired
        private BarbeiroRepository barbeiroRepository;


        public Agenda findById(Long agendaId) {
            return agendaRepository.findById(agendaId)
                    .orElseThrow(() -> new IllegalArgumentException("Agenda não encontrada com o ID: " + agendaId));
        }




        public List<Agenda> findAll() {
            return agendaRepository.findAll();
        }


    public Agenda save(Agenda agenda) {
        // Busca cliente e barbeiro no banco pelo ID
        Cliente cliente = clienteRepository.findById(agenda.getCliente().getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com o ID: " + agenda.getCliente().getClienteId()));

        Barbeiro barbeiro = barbeiroRepository.findById(agenda.getBarbeiro().getBarbeiroId())
                .orElseThrow(() -> new IllegalArgumentException("Barbeiro não encontrado com o ID: " + agenda.getBarbeiro().getBarbeiroId()));

        // Verifica se já há um agendamento nesse horário para o mesmo barbeiro
        if (agendaRepository.existsByBarbeiro_BarbeiroIdAndDataHora(barbeiro.getBarbeiroId(), agenda.getDataHora())) {
            throw new IllegalArgumentException("Já existe um agendamento para este barbeiro neste horário.");
        }

        agenda.setCliente(cliente);
        agenda.setBarbeiro(barbeiro);

        return agendaRepository.save(agenda);
    }

    public Agenda update(Long agendaId, Agenda novosDados) {
        // Verifica se a agenda existe
        Agenda agendaExistente = agendaRepository.findById(agendaId)
                .orElseThrow(() -> new IllegalArgumentException("Agenda não encontrada com o ID: " + agendaId));

        // Atualiza data/hora
        agendaExistente.setDataHora(novosDados.getDataHora());

        // Busca cliente e barbeiro pelos IDs recebidos
        Cliente cliente = clienteRepository.findById(novosDados.getCliente().getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com o ID: " + novosDados.getCliente().getClienteId()));

        Barbeiro barbeiro = barbeiroRepository.findById(novosDados.getBarbeiro().getBarbeiroId())
                .orElseThrow(() -> new IllegalArgumentException("Barbeiro não encontrado com o ID: " + novosDados.getBarbeiro().getBarbeiroId()));

        agendaExistente.setCliente(cliente);
        agendaExistente.setBarbeiro(barbeiro);

        return agendaRepository.save(agendaExistente);
    }
        public void delete(Long agendaId) {
            if (!agendaRepository.existsById(agendaId)) {
                throw new IllegalArgumentException("Agenda não encontrada com o ID: " + agendaId);
            }
            agendaRepository.deleteById(agendaId);
        }
    }

