package br.com.barbearia.serviceTest;

import br.com.barbearia.exceptions.ObjectNotFoundException;
import br.com.barbearia.models.Cliente;
import br.com.barbearia.repository.AgendaRepository;
import br.com.barbearia.repository.ClienteRepository;
import br.com.barbearia.services.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private AgendaRepository agendaRepository;

    private Cliente cliente;
    private Optional<Cliente> optionalCliente;

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
    void whenFindByIdThenReturnAnClienteInstance() {
        when(clienteRepository.findById(anyLong())).thenReturn(optionalCliente);

        Cliente response = clienteService.findById(ID);

        assertNotNull(response, "Response should not be null");
        assertEquals(Cliente.class, response.getClass(), "Response should be of type Cliente");
        assertEquals(ID, response.getClienteId(), "Cliente ID should match");
        assertEquals(NOME, response.getNome(), "Nome should match");
        assertEquals(EMAIL, response.getEmail(), "Email should match");
        assertEquals(TELEFONE, response.getTelefone(), "Telefone should match");
    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException() {
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.empty());

        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class,
                () -> clienteService.findById(ID),
                "Should throw ObjectNotFoundException when cliente not found");
        
        assertEquals("Cliente não encontrado", exception.getMessage(), "Exception message should match");
    }

    @Test
    void whenFindAllThenReturnAnListOfClientes() {
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));

        List<Cliente> response = clienteService.findAll();

        assertNotNull(response, "Response should not be null");
        assertEquals(1, response.size(), "Should have one cliente");
        assertEquals(Cliente.class, response.get(0).getClass(), "First item should be of type Cliente");
        assertEquals(ID, response.get(0).getClienteId(), "Cliente ID should match");
        assertEquals(NOME, response.get(0).getNome(), "Nome should match");
        assertEquals(EMAIL, response.get(0).getEmail(), "Email should match");
        assertEquals(TELEFONE, response.get(0).getTelefone(), "Telefone should match");
    }

    @Test
    void whenSaveWithValidDataThenReturnSuccess() {
        when(clienteRepository.save(any())).thenReturn(cliente);

        Cliente response = clienteService.save(cliente);

        assertNotNull(response, "Response should not be null");
        assertEquals(Cliente.class, response.getClass(), "Response should be of type Cliente");
        assertEquals(ID, response.getClienteId(), "Cliente ID should match");
        assertEquals(NOME, response.getNome(), "Nome should match");
        assertEquals(EMAIL, response.getEmail(), "Email should match");
        assertEquals(TELEFONE, response.getTelefone(), "Telefone should match");
        
        verify(clienteRepository, times(1)).save(any());
    }

    @Test
    void whenSaveWithEmptyNameThenThrowIllegalArgumentException() {
        Cliente clienteInvalido = new Cliente(ID, "", EMAIL, TELEFONE);
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> clienteService.save(clienteInvalido),
                "Should throw IllegalArgumentException when nome is empty");
        
        assertEquals("Nome do cliente não pode ser vazio", exception.getMessage(), 
                "Exception message should match");
        
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void whenSaveWithNullNameThenThrowIllegalArgumentException() {
        Cliente clienteInvalido = new Cliente(ID, null, EMAIL, TELEFONE);
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> clienteService.save(clienteInvalido),
                "Should throw IllegalArgumentException when nome is null");
        
        assertEquals("Nome do cliente não pode ser vazio", exception.getMessage(), 
                "Exception message should match");
        
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(clienteRepository.findById(anyLong())).thenReturn(optionalCliente);
        when(clienteRepository.save(any())).thenReturn(cliente);

        Cliente response = clienteService.update(ID, cliente);

        assertNotNull(response, "Response should not be null");
        assertEquals(Cliente.class, response.getClass(), "Response should be of type Cliente");
        assertEquals(ID, response.getClienteId(), "Cliente ID should match");
        assertEquals(NOME, response.getNome(), "Nome should match");
        assertEquals(EMAIL, response.getEmail(), "Email should match");
        assertEquals(TELEFONE, response.getTelefone(), "Telefone should match");
        
        verify(clienteRepository, times(1)).save(any());
        verify(clienteRepository, times(1)).findById(anyLong());
    }

    @Test
    void whenUpdateThenReturnAnObjectNotFoundException() {
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.empty());

        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class,
                () -> clienteService.update(ID, cliente),
                "Should throw ObjectNotFoundException when cliente not found");
        
        assertEquals("Cliente com ID " + ID + " não encontrado.", exception.getMessage(),
                "Exception message should match");
        
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void whenDeleteWithExistingClienteThenSuccess() {
        when(clienteRepository.findById(anyLong())).thenReturn(optionalCliente);
        when(agendaRepository.existsByCliente_ClienteId(anyLong())).thenReturn(false);
        
        assertDoesNotThrow(() -> clienteService.delete(ID),
                "Should not throw exception when deleting existing cliente without agendamento");
        
        verify(clienteRepository, times(1)).deleteById(ID);
        verify(agendaRepository, times(1)).existsByCliente_ClienteId(ID);
    }

    @Test
    void whenDeleteWithNonExistingClienteThenThrowIllegalArgumentException() {
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(agendaRepository.existsByCliente_ClienteId(anyLong())).thenReturn(false);
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> clienteService.delete(ID),
                "Should throw IllegalArgumentException when cliente not found");
        
        assertEquals("Cliente nao localizado", exception.getMessage(),
                "Exception message should match");
        
        verify(clienteRepository, never()).deleteById(anyLong());
    }

    @Test
    void whenDeleteWithExistingAgendamentoThenThrowObjectNotFoundException() {
        when(agendaRepository.existsByCliente_ClienteId(anyLong())).thenReturn(true);
        
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class,
                () -> clienteService.delete(ID),
                "Should throw ObjectNotFoundException when cliente has agendamento");
        
        assertEquals("Cliente possui agendamento e não pode ser excluído", exception.getMessage(),
                "Exception message should match");
        
        verify(clienteRepository, never()).deleteById(anyLong());
    }

    private void startCliente() {
        cliente = new Cliente(ID, NOME, EMAIL, TELEFONE);
        optionalCliente = Optional.of(cliente);
    }
}
