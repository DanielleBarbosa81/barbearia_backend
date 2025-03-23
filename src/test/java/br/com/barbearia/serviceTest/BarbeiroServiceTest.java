package br.com.barbearia.serviceTest;

import br.com.barbearia.exceptions.ObjectNotFoundException;
import br.com.barbearia.models.Barbeiro;
import br.com.barbearia.repository.BarbeiroRepository;
import br.com.barbearia.repository.AgendaRepository;
import br.com.barbearia.services.BarbeiroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class BarbeiroServiceTest {

    @InjectMocks
    BarbeiroService barbeiroService;

    @Mock
    BarbeiroRepository barbeiroRepository;

    @Mock
    AgendaRepository agendaRepository;


    private Long barbeiroId;
    private Barbeiro barbeiro;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

        barbeiroId = 1L;
        barbeiro = new Barbeiro();
        barbeiro.setBarbeiroId(barbeiroId);
        barbeiro.setNome("João");

    }


    @Test
    public void deveExcluirBarbeiroComSucesso() {
        // Simula o retorno do barbeiro ao buscar pelo ID
        when(barbeiroRepository.findById(barbeiroId)).thenReturn(Optional.of(barbeiro));

        // Simula que o barbeiro não possui agendamentos
        when(agendaRepository.existsByBarbeiroBarbeiroId(barbeiroId)).thenReturn(false);

        // Executa o método de exclusão
        barbeiroService.delete(barbeiroId);

        // Verifica se os métodos foram chamados corretamente
        verify(agendaRepository, times(1)).existsByBarbeiroBarbeiroId(barbeiroId);
        verify(barbeiroRepository, times(1)).findById(barbeiroId);
        verify(barbeiroRepository, times(1)).deleteById(barbeiroId);
    }


    @Test
    public void deveLancarExcecaoQuandoBarbeiroTemAgendamentos(){

        when(barbeiroRepository.findById(barbeiroId)).thenReturn(java.util.Optional.of(barbeiro));

        //simula que existem agendamentos associados ao barbeiro
        when(agendaRepository.existsByBarbeiroBarbeiroId(barbeiroId)).thenReturn(true);

        //verifica se a exceccao é lançada
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
            barbeiroService.delete(barbeiroId);
        });
        //verifica a mensagem de erro
        assertEquals("Não é possivel excluir barbeiro com cliente agendado!", exception.getMessage());

        //verifica se o metodo deleteById não foi chamado
        verify(barbeiroRepository, never()).deleteById(barbeiroId);
    }

}
