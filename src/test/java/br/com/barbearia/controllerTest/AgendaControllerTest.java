package br.com.barbearia.controllerTest;

import br.com.barbearia.controller.AgendaController;
import br.com.barbearia.dtos.AgendaDto;
import br.com.barbearia.models.Agenda;
import br.com.barbearia.models.Barbeiro;
import br.com.barbearia.models.Cliente;
import br.com.barbearia.services.AgendaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
class AgendaControllerTest {

    @InjectMocks
    private AgendaController agendaController;

    @Mock
    private AgendaService agendaService;

    @Mock
    private ModelMapper modelMapper;

    private Agenda agenda;
    private AgendaDto agendaDto;
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
        when(modelMapper.map(any(), eq(Agenda.class))).thenReturn(agenda);
        when(agendaService.save(any())).thenReturn(agenda);
        when(modelMapper.map(any(), eq(AgendaDto.class))).thenReturn(agendaDto);

        ResponseEntity<AgendaDto> response = agendaController.save(agendaDto);

        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be OK");
        
        AgendaDto body = response.getBody();
        assertNotNull(body, "Response body should not be null");
        assertEquals(AgendaDto.class, body.getClass(), "Body should be of type AgendaDto");
        assertEquals(cliente.getClienteId(), body.getClienteId(), "Cliente ID should match");
        assertEquals(barbeiro.getBarbeiroId(), body.getBarbeiroId(), "Barbeiro ID should match");
        assertEquals(dataHora, body.getDataHora(), "Data/Hora should match");
    }

    @Test
    void whenFindAllThenReturnSuccess() {
        when(agendaService.findAll()).thenReturn(List.of(agenda));
        when(modelMapper.map(any(), eq(AgendaDto.class))).thenReturn(agendaDto);

        ResponseEntity<List<AgendaDto>> response = agendaController.findAll();

        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be OK");
        
        List<AgendaDto> body = response.getBody();
        assertNotNull(body, "Response body should not be null");
        assertEquals(1, body.size(), "Should have one agenda");
        
        AgendaDto firstAgenda = body.get(0);
        assertNotNull(firstAgenda, "First agenda should not be null");
        assertEquals(AgendaDto.class, firstAgenda.getClass(), "First item should be of type AgendaDto");
        assertEquals(cliente.getClienteId(), firstAgenda.getClienteId(), "Cliente ID should match");
        assertEquals(barbeiro.getBarbeiroId(), firstAgenda.getBarbeiroId(), "Barbeiro ID should match");
        assertEquals(dataHora, firstAgenda.getDataHora(), "Data/Hora should match");
    }

    @Test
    void whenBuscarDatasAgendadasThenReturnSuccess() {
        when(agendaService.buscarPorTodasDatasAgendadas()).thenReturn(List.of(dataHora));

        ResponseEntity<List<String>> response = agendaController.buscarDatasAgendadas();

        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be OK");
        
        List<String> body = response.getBody();
        assertNotNull(body, "Response body should not be null");
        assertEquals(1, body.size(), "Should have one data agendada");
        assertTrue(body.get(0).matches("\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}"), "Date format should match dd-MM-yyyy HH:mm");
    }

    private void startAgenda() {
        cliente = new Cliente(ID, "João", "joao@email.com", "999999999");
        barbeiro = new Barbeiro(ID, "Pedro", "Corte masculino");
        dataHora = LocalDateTime.now();
        agenda = new Agenda(ID, cliente, barbeiro, dataHora);
        
        agendaDto = new AgendaDto(cliente.getClienteId(), barbeiro.getBarbeiroId(), dataHora);
    }
}
