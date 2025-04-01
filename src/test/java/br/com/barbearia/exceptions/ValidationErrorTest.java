package br.com.barbearia.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ValidationErrorTest {

    private static final LocalDateTime TIMESTAMP = LocalDateTime.now();
    private static final Integer STATUS = 400;
    private static final String MESSAGE = "Erro de validação";
    private static final String PATH = "/api/v1/resource";
    private static final String FIELD_NAME = "nome";
    private static final String FIELD_MESSAGE = "Campo obrigatório";

    @Test
    void whenValidationErrorThenCheckAllFields() {
        ValidationError error = new ValidationError(TIMESTAMP, STATUS, MESSAGE, PATH);
        
        assertAll("ValidationError validation",
            () -> assertEquals(TIMESTAMP, error.getTimestamp()),
            () -> assertEquals(STATUS, error.getStatus()),
            () -> assertEquals(MESSAGE, error.getMessage()),
            () -> assertEquals(PATH, error.getPath()),
            () -> assertTrue(error.getErro().isEmpty())
        );
    }

    @Test
    void whenAddErrorThenCheckFieldMessage() {
        ValidationError error = new ValidationError(TIMESTAMP, STATUS, MESSAGE, PATH);
        error.addErro(FIELD_NAME, FIELD_MESSAGE);
        
        List<FieldMessage> errors = error.getErro();
        
        assertAll("ValidationError addError validation",
            () -> assertEquals(1, errors.size()),
            () -> assertEquals(FIELD_NAME, errors.get(0).getFieldName()),
            () -> assertEquals(FIELD_MESSAGE, errors.get(0).getMessage())
        );
    }
}
