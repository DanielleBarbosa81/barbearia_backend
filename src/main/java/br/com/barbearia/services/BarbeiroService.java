package br.com.barbearia.services;

import br.com.barbearia.models.Barbeiro;
import br.com.barbearia.models.Cliente;
import br.com.barbearia.repository.BarbeiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class BarbeiroService {

    @Autowired
    private BarbeiroRepository barbeiroRepository;


    public Barbeiro findById(Long id){
        Optional<Barbeiro> barbeiro = barbeiroRepository.findById(id);
        if(barbeiro.isPresent()){
            return barbeiro.get();

        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Barbeiro não localizado!");
    }


    public List<Barbeiro>pesquisarBarbeiros(){
        return barbeiroRepository.findAll();
    }

    public List<Barbeiro> pesquisarPorNome(String nome){
        return barbeiroRepository.findByNomeContainingIgnoreCase(nome);
    }
    public List<Barbeiro> pesquisarPorEspecialidade (String especialidade){
        return barbeiroRepository.findByEspecialidadeContainingIgnoreCase(especialidade);
    }

        public Barbeiro save (Barbeiro barbeiro){
        if(barbeiro.getId() != null && barbeiroRepository.existsById(barbeiro.getId())){
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Barbeiro já cadastrado!");
        }
        return barbeiroRepository.save(barbeiro);
    }
    public boolean excluirBarbeiro(Long id) {
        if (barbeiroRepository.existsById(id)) {
            barbeiroRepository.deleteById(id);  // Exclui o Barbeiro do banco
            return true;  // Retorna true se a exclusão for bem-sucedida
        }
        return false;

    }

}
