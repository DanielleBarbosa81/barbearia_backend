package br.com.barbearia.serviceTest;

import br.com.barbearia.exceptions.ObjectNotFoundException;
import br.com.barbearia.models.Barbeiro;
import br.com.barbearia.repository.AgendaRepository;
import br.com.barbearia.repository.BarbeiroRepository;
import br.com.barbearia.services.BarbeiroService;
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
class BarbeiroServiceTest {

    @InjectMocks
    private BarbeiroService barbeiroService;

    @Mock
    private BarbeiroRepository barbeiroRepository;

    @Mock
    private AgendaRepository agendaRepository;

    private Barbeiro barbeiro;
    private Optional<Barbeiro> optionalBarbeiro;

    private static final Long ID = 1L;
    private static final String NOME = "Pedro";
    private static final String ESPECIALIDADE = "Corte masculino";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startBarbeiro();
    }

    @Test
    void whenFindByIdThenReturnAnBarbeiroInstance() {
        when(barbeiroRepository.findById(anyLong())).thenReturn(optionalBarbeiro);

        Barbeiro response = barbeiroService.findById(ID);

        assertNotNull(response);
        assertEquals(Barbeiro.class, response.getClass());
        assertEquals(ID, response.getBarbeiroId());
        assertEquals(NOME, response.getNome());
        assertEquals(ESPECIALIDADE, response.getEspecialidade());
    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException() {
        when(barbeiroRepository.findById(anyLong())).thenReturn(Optional.empty());

        try {
            barbeiroService.findById(ID);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("Barbeiro não encontrado", ex.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnAnListOfBarbeiros() {
        when(barbeiroRepository.findAll()).thenReturn(List.of(barbeiro));

        List<Barbeiro> response = barbeiroService.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(Barbeiro.class, response.get(0).getClass());
        assertEquals(ID, response.get(0).getBarbeiroId());
        assertEquals(NOME, response.get(0).getNome());
        assertEquals(ESPECIALIDADE, response.get(0).getEspecialidade());
    }

    @Test
    void whenSaveThenReturnSuccess() {
        when(barbeiroRepository.save(any())).thenReturn(barbeiro);

        Barbeiro response = barbeiroService.save(barbeiro);

        assertNotNull(response);
        assertEquals(Barbeiro.class, response.getClass());
        assertEquals(ID, response.getBarbeiroId());
        assertEquals(NOME, response.getNome());
        assertEquals(ESPECIALIDADE, response.getEspecialidade());
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(barbeiroRepository.findById(anyLong())).thenReturn(optionalBarbeiro);
        when(barbeiroRepository.save(any())).thenReturn(barbeiro);

        Barbeiro response = barbeiroService.update(ID, barbeiro);

        assertNotNull(response);
        assertEquals(Barbeiro.class, response.getClass());
        assertEquals(ID, response.getBarbeiroId());
        assertEquals(NOME, response.getNome());
        assertEquals(ESPECIALIDADE, response.getEspecialidade());
    }

    @Test
    void whenUpdateThenReturnAnObjectNotFoundException() {
        when(barbeiroRepository.findById(anyLong())).thenReturn(Optional.empty());

        try {
            barbeiroService.update(ID, barbeiro);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("Barbeiro não encontrado", ex.getMessage());
        }
    }

    private void startBarbeiro() {
        barbeiro = new Barbeiro(ID, NOME, ESPECIALIDADE);
        optionalBarbeiro = Optional.of(barbeiro);
    }
}
