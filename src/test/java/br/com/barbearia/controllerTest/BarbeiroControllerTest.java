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

}
