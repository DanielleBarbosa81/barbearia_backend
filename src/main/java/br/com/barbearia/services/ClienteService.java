package br.com.barbearia.services;

import br.com.barbearia.exceptions.ObjectNotFoundException;
import br.com.barbearia.models.Barbeiro;
import br.com.barbearia.models.Cliente;
import br.com.barbearia.repository.AgendaRepository;
import br.com.barbearia.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private AgendaRepository agendaRepository;


    public Cliente findById(Long clienteId){
        Optional<Cliente> cliente = clienteRepository.findById(clienteId);
        if(cliente.isPresent()){
            return cliente.get();
        }
        throw new ObjectNotFoundException("Cliente não encontrado");
    }
    public List<Cliente> findAll (){
        return clienteRepository.findAll();
    }

    public Cliente save(Cliente cliente) {
        if (cliente.getNome() == null || cliente.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome do cliente não pode ser vazio");
        }
        return clienteRepository.save(cliente);
    }


    public Cliente update(Long clienteId, Cliente novosDados) {
        // Verifica se o cliente existe no banco de dados
        Cliente clienteExistente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ObjectNotFoundException("Cliente com ID " + clienteId + " não encontrado."));

        // Atualiza os dados do cliente existente com os novos dados recebidos
        clienteExistente.setNome(novosDados.getNome());
        clienteExistente.setEmail(novosDados.getEmail());
        clienteExistente.setTelefone(novosDados.getTelefone());
        // Inclua outros campos que você deseja atualizar...

        // Salva o cliente atualizado no banco de dados
        return clienteRepository.save(clienteExistente);
    }


    public void delete (Long clienteId){

        //verificar antes se existe um agendamento para o cliente
        boolean possuiAgendamento = agendaRepository.existsByCliente_ClienteId(clienteId);

        if(possuiAgendamento){
            throw new ObjectNotFoundException("Cliente possui agendamento e não pode ser excluído");
        }
        Optional<Cliente> clienteOptional = clienteRepository.findById(clienteId);
        if(clienteOptional.isPresent()){
            clienteRepository.deleteById(clienteId);
            System.out.println("Cliente com o id: " + clienteId +  " foi excluído com sucesso");
        }else{
           throw new IllegalArgumentException("Cliente nao localizado");
        }

    }

}
