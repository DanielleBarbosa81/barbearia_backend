package br.com.barbearia.controllerTest;

import br.com.barbearia.controller.ClienteController;
import br.com.barbearia.dtos.ClienteDto;
import br.com.barbearia.models.Cliente;
import br.com.barbearia.services.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
class ClienteControllerTest {

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteService clienteService;

    @Mock
    private ModelMapper modelMapper;

    private Cliente cliente;
    private ClienteDto clienteDto;

    private static final Long ID = 1L;
    private static final String NOME = "João";
    private static final String EMAIL = "joao@email.com";
    private static final String TELEFONE = "999999999";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startCliente();
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        when(clienteService.findById(anyLong())).thenReturn(cliente);
        when(modelMapper.map(any(), eq(ClienteDto.class))).thenReturn(clienteDto);

        ResponseEntity<ClienteDto> response = clienteController.findById(ID);

        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be OK");
        
        ClienteDto body = response.getBody();
        assertNotNull(body, "Response body should not be null");
        assertEquals(ClienteDto.class, body.getClass(), "Body should be of type ClienteDto");
        assertEquals(ID, body.getClienteId(), "Cliente ID should match");
        assertEquals(NOME, body.getNome(), "Nome should match");
        assertEquals(EMAIL, body.getEmail(), "Email should match");
        assertEquals(TELEFONE, body.getTelefone(), "Telefone should match");
    }

    @Test
    void whenFindAllThenReturnListOfClienteDTO() {
        when(clienteService.findAll()).thenReturn(List.of(cliente));
        when(modelMapper.map(any(), eq(ClienteDto.class))).thenReturn(clienteDto);

        ResponseEntity<List<ClienteDto>> response = clienteController.findAll();

        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be OK");
        
        List<ClienteDto> body = response.getBody();
        assertNotNull(body, "Response body should not be null");
        assertEquals(1, body.size(), "Should have one cliente");
        
        ClienteDto firstCliente = body.get(0);
        assertNotNull(firstCliente, "First cliente should not be null");
        assertEquals(ClienteDto.class, firstCliente.getClass(), "First item should be of type ClienteDto");
        assertEquals(ID, firstCliente.getClienteId(), "Cliente ID should match");
        assertEquals(NOME, firstCliente.getNome(), "Nome should match");
        assertEquals(EMAIL, firstCliente.getEmail(), "Email should match");
        assertEquals(TELEFONE, firstCliente.getTelefone(), "Telefone should match");
    }

    @Test
    void whenSaveThenReturnSuccess() {
        when(modelMapper.map(any(), eq(Cliente.class))).thenReturn(cliente);
        when(clienteService.save(any())).thenReturn(cliente);
        when(modelMapper.map(any(), eq(ClienteDto.class))).thenReturn(clienteDto);

        ResponseEntity<ClienteDto> response = clienteController.save(clienteDto);

        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be OK");
        
        ClienteDto body = response.getBody();
        assertNotNull(body, "Response body should not be null");
        assertEquals(ClienteDto.class, body.getClass(), "Body should be of type ClienteDto");
        assertEquals(ID, body.getClienteId(), "Cliente ID should match");
        assertEquals(NOME, body.getNome(), "Nome should match");
        assertEquals(EMAIL, body.getEmail(), "Email should match");
        assertEquals(TELEFONE, body.getTelefone(), "Telefone should match");
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(modelMapper.map(any(), eq(Cliente.class))).thenReturn(cliente);
        when(clienteService.update(anyLong(), any(Cliente.class))).thenReturn(cliente);
        when(modelMapper.map(any(), eq(ClienteDto.class))).thenReturn(clienteDto);

        ResponseEntity<ClienteDto> response = clienteController.update(ID, clienteDto);

        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be OK");
        
        ClienteDto body = response.getBody();
        assertNotNull(body, "Response body should not be null");
        assertEquals(ClienteDto.class, body.getClass(), "Body should be of type ClienteDto");
        assertEquals(ID, body.getClienteId(), "Cliente ID should match");
        assertEquals(NOME, body.getNome(), "Nome should match");
        assertEquals(EMAIL, body.getEmail(), "Email should match");
        assertEquals(TELEFONE, body.getTelefone(), "Telefone should match");
    }

    private void startCliente() {
        cliente = new Cliente(ID, NOME, EMAIL, TELEFONE);
        clienteDto = new ClienteDto();
        clienteDto.setClienteId(ID);
        clienteDto.setNome(NOME);
        clienteDto.setEmail(EMAIL);
        clienteDto.setTelefone(TELEFONE);
    }
}
