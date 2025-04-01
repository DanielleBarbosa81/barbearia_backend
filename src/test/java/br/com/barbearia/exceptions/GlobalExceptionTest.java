package br.com.barbearia.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class GlobalExceptionTest {

    @InjectMocks
    private GlobalException globalException;

    private MockHttpServletRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new MockHttpServletRequest();
        request.setRequestURI("/api/v1/resource");
    }

    @Test
    void whenObjectNotFoundExceptionThenReturnResponseEntity() {
        ObjectNotFoundException exception = new ObjectNotFoundException("Objeto não encontrado");
        
        ResponseEntity<StandardError> response = globalException.handleObjectNotFoundException(exception, request);
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Status code should be NOT_FOUND");
        
        StandardError body = response.getBody();
        assertNotNull(body, "Response body should not be null");
        
        assertEquals("Objeto não encontrado", body.getMessage(), "Message should match");
        assertEquals("/api/v1/resource", body.getPath(), "Path should match");
        assertEquals(HttpStatus.NOT_FOUND.value(), body.getStatus(), "Status should match");
    }

    @Test
    void whenIllegalArgumentExceptionThenReturnResponseEntity() {
        IllegalArgumentException exception = new IllegalArgumentException("Argumento inválido");
        
        ResponseEntity<StandardError> response = globalException.handleIllegalArgumentException(exception, request);
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Status code should be BAD_REQUEST");
        
        StandardError body = response.getBody();
        assertNotNull(body, "Response body should not be null");
        
        assertEquals("Argumento inválido", body.getMessage(), "Message should match");
        assertEquals("/api/v1/resource", body.getPath(), "Path should match");
        assertEquals(HttpStatus.BAD_REQUEST.value(), body.getStatus(), "Status should match");
    }

    @Test
    void whenDataIntegrityViolationExceptionThenReturnResponseEntity() {
        DataIntegrityViolationException exception = new DataIntegrityViolationException("Violação de integridade");
        
        ResponseEntity<StandardError> response = globalException.handleDataIntegrityViolationException(exception, request);
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode(), "Status code should be CONFLICT");
        
        StandardError body = response.getBody();
        assertNotNull(body, "Response body should not be null");
        
        assertEquals("Violação de integridade dos dados. Verifique suas referências ou restrições.", 
                body.getMessage(), "Message should match");
        assertEquals("/api/v1/resource", body.getPath(), "Path should match");
        assertEquals(HttpStatus.CONFLICT.value(), body.getStatus(), "Status should match");
    }

    @Test
    void whenMethodArgumentNotValidExceptionThenReturnResponseEntity() {
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        
        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(
            new FieldError("object", "field1", "Mensagem de erro 1"),
            new FieldError("object", "field2", "Mensagem de erro 2")
        ));
        
        ResponseEntity<ValidationError> response = globalException.handleMethodArgumentNotValidException(exception, request);
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Status code should be BAD_REQUEST");
        
        ValidationError body = response.getBody();
        assertNotNull(body, "Response body should not be null");
        
        assertEquals("Erro na validação dos campos.", body.getMessage(), "Message should match");
        assertEquals("/api/v1/resource", body.getPath(), "Path should match");
        assertEquals(HttpStatus.BAD_REQUEST.value(), body.getStatus(), "Status should match");
        
        List<FieldMessage> errors = body.getErro();
        assertNotNull(errors, "Errors list should not be null");
        assertEquals(2, errors.size(), "Should have 2 validation errors");
        
        assertEquals("field1", errors.get(0).getFieldName(), "First error field name should match");
        assertEquals("Mensagem de erro 1", errors.get(0).getMessage(), "First error message should match");
        assertEquals("field2", errors.get(1).getFieldName(), "Second error field name should match");
        assertEquals("Mensagem de erro 2", errors.get(1).getMessage(), "Second error message should match");
    }
}
