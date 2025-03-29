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

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    private LocalDateTime dataHora;

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
        when(agendaRepository.existsByBarbeiro_BarbeiroIdAndDataHora(barbeiroId, dataHora)).thenReturn(false);

        // Executa o método de exclusão
        barbeiroService.delete(barbeiroId, dataHora);

        // Verifica se os métodos foram chamados corretamente
        verify(agendaRepository, times(1)).existsByBarbeiro_BarbeiroIdAndDataHora(barbeiroId, dataHora);
        verify(barbeiroRepository, times(1)).findById(barbeiroId);
        verify(barbeiroRepository, times(1)).deleteById(barbeiroId);
    }


    @Test
    public void deveLancarExcecaoQuandoBarbeiroTemAgendamentos(){

        when(barbeiroRepository.findById(barbeiroId)).thenReturn(java.util.Optional.of(barbeiro));

        //simula que existem agendamentos associados ao barbeiro
        when(agendaRepository.existsByBarbeiro_BarbeiroIdAndDataHora(barbeiroId, dataHora)).thenReturn(true);

        //verifica se a exceccao é lançada
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
            barbeiroService.delete(barbeiroId, dataHora);
        });
        //verifica a mensagem de erro
        assertEquals("Não é possivel excluir barbeiro com cliente agendado!", exception.getMessage());

        //verifica se o metodo deleteById não foi chamado
        verify(barbeiroRepository, never()).deleteById(barbeiroId);
    }
    @Test
    public void testExistsByBarbeiroAndDataHora() {
        Long barbeiroId = 1L;
        LocalDateTime dataHora = LocalDateTime.of(2023, 12, 31, 15, 0);

        boolean resultado = agendaRepository.existsByBarbeiro_BarbeiroIdAndDataHora(barbeiroId, dataHora);

        assertFalse(resultado); // Verifica se não há agendamento para o horário
    }


}
