package br.com.barbearia.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FieldMessageTest {

    private static final String FIELD_NAME = "nome";
    private static final String MESSAGE = "Campo obrigatório";

    @Test
    void whenFieldMessageThenCheckAllFields() {
        FieldMessage fieldMessage = new FieldMessage(FIELD_NAME, MESSAGE);
        
        assertAll("FieldMessage validation",
            () -> assertEquals(FIELD_NAME, fieldMessage.getFieldName()),
            () -> assertEquals(MESSAGE, fieldMessage.getMessage())
        );
    }

    @Test
    void whenFieldMessageSettersThenCheckValues() {
        FieldMessage fieldMessage = new FieldMessage();
        fieldMessage.setFieldName(FIELD_NAME);
        fieldMessage.setMessage(MESSAGE);
        
        assertAll("FieldMessage setters validation",
            () -> assertEquals(FIELD_NAME, fieldMessage.getFieldName()),
            () -> assertEquals(MESSAGE, fieldMessage.getMessage())
        );
    }
}
