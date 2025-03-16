package br.com.barbearia;

import br.com.barbearia.models.Cliente;
import br.com.barbearia.repository.ClienteRepository;
import br.com.barbearia.services.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    private Cliente clienteExistente;
    private Cliente novosDados;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        /* Testando o endpoint update
         Configuração inicial de um cliente existente*/
        clienteExistente = new Cliente();
        clienteExistente.setClienteId(1L);
        clienteExistente.setNome("Fulano");
        clienteExistente.setEmail("fulano@kakaka.com");
        clienteExistente.setTelefone("8181818181");

        // Configuração inicial de novos dados
        novosDados = new Cliente();
        novosDados.setNome("Maria");
        novosDados.setEmail("maria@kakaka.com");
        novosDados.setTelefone("99990000");
    }

    @Test
    public void testAtualizarCliente() {
        // Simula o comportamento do repositório ao buscar o cliente
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteExistente));

        // Simula o comportamento do repositório ao salvar o cliente atualizado
        when(clienteRepository.save(clienteExistente)).thenReturn(clienteExistente);

        // Executa o método de atualização
        Cliente clienteAtualizado = clienteService.update(1L, novosDados);

        // Verifica se os dados foram atualizados corretamente
        assertNotNull(clienteAtualizado);
        assertEquals("Maria", clienteAtualizado.getNome());
        assertEquals("maria@kakaka.com", clienteAtualizado.getEmail());
        assertEquals("99990000", clienteAtualizado.getTelefone());

        // Verifica se os métodos do repositório foram chamados
        verify(clienteRepository, times(1)).findById(1L);
        verify(clienteRepository, times(1)).save(clienteExistente);
    }
}
