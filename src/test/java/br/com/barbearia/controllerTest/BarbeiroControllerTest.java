package br.com.barbearia.controllerTest;

import br.com.barbearia.controller.BarbeiroController;
import br.com.barbearia.dtos.BarbeiroDto;
import br.com.barbearia.models.Barbeiro;
import br.com.barbearia.services.BarbeiroService;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
class BarbeiroControllerTest {

    @InjectMocks
    private BarbeiroController barbeiroController;

    @Mock
    private BarbeiroService barbeiroService;

    @Mock
    private ModelMapper modelMapper;

    private Barbeiro barbeiro;
    private BarbeiroDto barbeiroDto;
    private LocalDateTime dataHora;

    private static final Long ID = 1L;
    private static final String NOME = "Pedro";
    private static final String ESPECIALIDADE = "Corte masculino";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startBarbeiro();
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        when(barbeiroService.findById(anyLong())).thenReturn(barbeiro);
        when(modelMapper.map(any(Barbeiro.class), eq(BarbeiroDto.class))).thenReturn(barbeiroDto);

        ResponseEntity<BarbeiroDto> response = barbeiroController.findById(ID);

        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be OK");
        
        BarbeiroDto body = response.getBody();
        assertNotNull(body, "Response body should not be null");
        assertEquals(BarbeiroDto.class, body.getClass(), "Body should be of type BarbeiroDto");
        assertEquals(ID, body.getBarbeiroId(), "Barbeiro ID should match");
        assertEquals(NOME, body.getNome(), "Nome should match");
        assertEquals(ESPECIALIDADE, body.getEspecialidade(), "Especialidade should match");
    }

    @Test
    void whenFindAllThenReturnListOfBarbeiroDTO() {
        when(barbeiroService.findAll()).thenReturn(List.of(barbeiro));
        when(modelMapper.map(any(Barbeiro.class), eq(BarbeiroDto.class))).thenReturn(barbeiroDto);

        ResponseEntity<List<BarbeiroDto>> response = barbeiroController.findAll(0L);

        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be OK");
        
        List<BarbeiroDto> body = response.getBody();
        assertNotNull(body, "Response body should not be null");
        assertEquals(1, body.size(), "Should have one barbeiro");
        
        BarbeiroDto firstBarbeiro = body.get(0);
        assertNotNull(firstBarbeiro, "First barbeiro should not be null");
        assertEquals(BarbeiroDto.class, firstBarbeiro.getClass(), "First item should be of type BarbeiroDto");
        assertEquals(ID, firstBarbeiro.getBarbeiroId(), "Barbeiro ID should match");
        assertEquals(NOME, firstBarbeiro.getNome(), "Nome should match");
        assertEquals(ESPECIALIDADE, firstBarbeiro.getEspecialidade(), "Especialidade should match");
    }

    @Test
    void whenSaveThenReturnSuccess() {
        when(modelMapper.map(any(BarbeiroDto.class), eq(Barbeiro.class))).thenReturn(barbeiro);
        when(barbeiroService.save(any(Barbeiro.class))).thenReturn(barbeiro);
        when(modelMapper.map(any(Barbeiro.class), eq(BarbeiroDto.class))).thenReturn(barbeiroDto);

        ResponseEntity<BarbeiroDto> response = barbeiroController.save(barbeiroDto);

        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be OK");
        
        BarbeiroDto body = response.getBody();
        assertNotNull(body, "Response body should not be null");
        assertEquals(BarbeiroDto.class, body.getClass(), "Body should be of type BarbeiroDto");
        assertEquals(ID, body.getBarbeiroId(), "Barbeiro ID should match");
        assertEquals(NOME, body.getNome(), "Nome should match");
        assertEquals(ESPECIALIDADE, body.getEspecialidade(), "Especialidade should match");
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(modelMapper.map(any(BarbeiroDto.class), eq(Barbeiro.class))).thenReturn(barbeiro);
        when(barbeiroService.update(anyLong(), any(Barbeiro.class))).thenReturn(barbeiro);
        when(modelMapper.map(any(Barbeiro.class), eq(BarbeiroDto.class))).thenReturn(barbeiroDto);

        ResponseEntity<BarbeiroDto> response = barbeiroController.update(ID, barbeiroDto);

        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be OK");
        
        BarbeiroDto body = response.getBody();
        assertNotNull(body, "Response body should not be null");
        assertEquals(BarbeiroDto.class, body.getClass(), "Body should be of type BarbeiroDto");
        assertEquals(ID, body.getBarbeiroId(), "Barbeiro ID should match");
        assertEquals(NOME, body.getNome(), "Nome should match");
        assertEquals(ESPECIALIDADE, body.getEspecialidade(), "Especialidade should match");
    }

    @Test
    void whenDeleteThenReturnSuccess() {
        doNothing().when(barbeiroService).delete(anyLong(), any(LocalDateTime.class));

        ResponseEntity<Void> response = barbeiroController.delete(ID, dataHora);

        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode(), "Status code should be NO_CONTENT");
        verify(barbeiroService, times(1)).delete(eq(ID), eq(dataHora));
    }

    private void startBarbeiro() {
        dataHora = LocalDateTime.now();
        barbeiro = new Barbeiro(ID, NOME, ESPECIALIDADE);
        barbeiroDto = new BarbeiroDto(ID, NOME, ESPECIALIDADE);
    }
}
