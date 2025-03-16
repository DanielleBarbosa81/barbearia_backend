package br.com.barbearia.services;

import br.com.barbearia.exceptions.ObjectNotFoundException;
import br.com.barbearia.models.Barbeiro;
import br.com.barbearia.repository.AgendaRepository;
import br.com.barbearia.repository.BarbeiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class BarbeiroService {

    @Autowired
    private BarbeiroRepository barbeiroRepository;

    @Autowired
    private AgendaRepository agendaRepository;


    public Barbeiro findById (Long barbeiroId){
        Optional<Barbeiro> barbeiro = barbeiroRepository.findById(barbeiroId);
        if(barbeiro.isPresent()){
            return barbeiro.get();
        }
        throw new ObjectNotFoundException("Barbeiro não encontrado");
    }
    public List<Barbeiro> findAll (){

        return barbeiroRepository.findAll();
    }


       public Barbeiro save (Barbeiro barbeiro){
        return barbeiroRepository.save(barbeiro);
    }

    public Barbeiro update (Long barbeiroId , Barbeiro novosDados){
       Optional<Barbeiro> barbeiroExistente = barbeiroRepository.findById(barbeiroId);
       if(barbeiroExistente.isPresent()){
           Barbeiro barbeiro = barbeiroExistente.get();
           barbeiro.setNome(novosDados.getNome());
           barbeiro.setEspecialidade(novosDados.getEspecialidade());

          return barbeiroRepository.save(barbeiro);

       }else{
           throw new ObjectNotFoundException("Barbeiro nao encontrado");
       }
    }

    public void delete (Long barbeiroId) {
        Barbeiro barbeiro = findById(barbeiroId);
        boolean temAgendamentos  = agendaRepository.existsByBarbeiroBarbeiroId(barbeiroId);

              if(temAgendamentos){
                   throw new ObjectNotFoundException("Não é possivel excluir barbeiro com cliente agendado!");
        }
        barbeiroRepository.deleteById(barbeiroId);
        System.out.println("Barbeiro com o id: " + barbeiroId +  " foi excluído com sucesso");
    }
}


