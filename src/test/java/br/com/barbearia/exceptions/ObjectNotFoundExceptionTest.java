package br.com.barbearia.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ObjectNotFoundExceptionTest {

    private static final String MESSAGE = "Objeto não encontrado";

    @Test
    void whenExceptionThenCheckMessage() {
        ObjectNotFoundException exception = new ObjectNotFoundException(MESSAGE);
        assertEquals(MESSAGE, exception.getMessage());
    }

    @Test
    void whenExceptionThenCheckInstance() {
        ObjectNotFoundException exception = new ObjectNotFoundException(MESSAGE);
        assertEquals(ObjectNotFoundException.class, exception.getClass());
        assertTrue(exception instanceof RuntimeException);
    }
}
