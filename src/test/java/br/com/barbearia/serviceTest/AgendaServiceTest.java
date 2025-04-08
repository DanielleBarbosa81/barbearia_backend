package br.com.barbearia.serviceTest;

import br.com.barbearia.exceptions.ObjectNotFoundException;
import br.com.barbearia.models.Agenda;
import br.com.barbearia.models.Barbeiro;
import br.com.barbearia.models.Cliente;
import br.com.barbearia.repository.AgendaRepository;
import br.com.barbearia.services.AgendaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class AgendaServiceTest {

    @InjectMocks
    private AgendaService agendaService;

    @Mock
    private AgendaRepository agendaRepository;

    private Agenda agenda;
    private Cliente cliente;
    private Barbeiro barbeiro;
    private LocalDateTime dataHora;

    private static final Long ID = 1L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startAgenda();
    }

    @Test
    void whenSaveThenReturnSuccess() {
        when(agendaRepository.existsByBarbeiro_BarbeiroIdAndDataHora(anyLong())).thenReturn(false);
        when(agendaRepository.save(any())).thenReturn(agenda);

        Agenda response = agendaService.save(agenda);

        assertNotNull(response);
        assertEquals(Agenda.class, response.getClass());
        assertEquals(ID, response.getAgendaId());
        assertEquals(cliente, response.getCliente());
        assertEquals(barbeiro, response.getBarbeiro());
        assertEquals(dataHora, response.getDataHora());
    }

    @Test
    void whenSaveWithExistingAppointmentThenThrowException() {
        when(agendaRepository.existsByBarbeiro_BarbeiroIdAndDataHora(anyLong())).thenReturn(true);

        try {
            agendaService.save(agenda);
            fail("Expected IllegalArgumentException");
        } catch (Exception ex) {
            assertEquals(IllegalArgumentException.class, ex.getClass());
            assertEquals("Já existe um agendamento para este barbeiro neste horário.", ex.getMessage());
        }
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(agendaRepository.findById(anyLong())).thenReturn(Optional.of(agenda));
        when(agendaRepository.save(any())).thenReturn(agenda);

        Agenda response = agendaService.atualizarAgenda(ID, agenda);

        assertNotNull(response);
        assertEquals(Agenda.class, response.getClass());
        assertEquals(ID, response.getAgendaId());
        assertEquals(cliente, response.getCliente());
        assertEquals(barbeiro, response.getBarbeiro());
        assertEquals(dataHora, response.getDataHora());
    }

    @Test
    void whenUpdateWithNonexistentIdThenThrowException() {
        when(agendaRepository.findById(anyLong())).thenReturn(Optional.empty());

        try {
            agendaService.atualizarAgenda(ID, agenda);
            fail("Expected ObjectNotFoundException");
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("Agendamento com ID " + ID + " não localizado.", ex.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnList() {
        when(agendaRepository.findAll()).thenReturn(List.of(agenda));

        List<LocalDateTime> response = agendaService.buscarPorTodasDatasAgendadas();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(dataHora, response.get(0));
    }

    @Test
    void whenDeleteAppointmentThenSuccess() {
        when(agendaRepository.findAll()).thenReturn(List.of(agenda));
        doNothing().when(agendaRepository).delete(any());

        agendaService.excluirAgendamentoDoBarbeiro(barbeiro.getBarbeiroId(), dataHora);

        verify(agendaRepository, times(1)).delete(any());
    }

    @Test
    void whenDeleteNonexistentAppointmentThenThrowException() {
        when(agendaRepository.findAll()).thenReturn(List.of());

        try {
            agendaService.excluirAgendamentoDoBarbeiro(barbeiro.getBarbeiroId(), dataHora);
            fail("Expected ObjectNotFoundException");
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("Agendamento não encontrado para o barbeiro no horário especificado", ex.getMessage());
        }
    }

    private void startAgenda() {
        cliente = new Cliente(ID, "João", "joao@email.com", "999999999");
        barbeiro = new Barbeiro(ID, "Pedro", "Corte masculino");
        dataHora = LocalDateTime.now();
        agenda = new Agenda(ID, cliente, barbeiro, dataHora);
    }
}
