package br.com.barbearia.services;

import br.com.barbearia.models.Cliente;
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

    public Cliente findById(Long id){
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if(cliente.isPresent()){
            return cliente.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");

    }

    public Cliente cadastrarCliente (Cliente cliente) {
        if (clienteRepository.existsById(cliente.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente já cadastrado");

        }
       return clienteRepository.save(cliente);
    }

    public List<Cliente> pesquisarClientes(){
        return clienteRepository.findAll();
    }

    public List<Cliente> pesquisarPorNome(String nome){
        return clienteRepository.findByNomeContainingIgnoreCase(nome);
    }
    public Cliente delete (Long id) {
        Cliente cliente =   findById(id);
        clienteRepository.deleteById(id);
        return cliente;
    }

    public Cliente save (Cliente cliente){
        if (cliente.getId() !=null && clienteRepository.existsById(cliente.getId()) ){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente já cadastrado!");

        }   return clienteRepository.save(cliente);
    }
}
