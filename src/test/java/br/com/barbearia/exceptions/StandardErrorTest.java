package br.com.barbearia.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StandardErrorTest {

    private static final LocalDateTime TIMESTAMP = LocalDateTime.now();
    private static final Integer STATUS = HttpStatus.NOT_FOUND.value();
    private static final String MESSAGE = "Objeto não encontrado";
    private static final String PATH = "/api/v1/resource";

    @Test
    void whenStandardErrorThenCheckAllFields() {
        StandardError error = new StandardError(TIMESTAMP, STATUS, MESSAGE, PATH);
        
        assertAll("StandardError validation",
            () -> assertEquals(TIMESTAMP, error.getTimestamp()),
            () -> assertEquals(STATUS, error.getStatus()),
            () -> assertEquals(MESSAGE, error.getMessage()),
            () -> assertEquals(PATH, error.getPath())
        );
    }

    @Test
    void whenStandardErrorSettersThenCheckValues() {
        StandardError error = new StandardError();
        error.setTimestamp(TIMESTAMP);
        error.setStatus(STATUS);
        error.setMessage(MESSAGE);
        error.setPath(PATH);
        
        assertAll("StandardError setters validation",
            () -> assertEquals(TIMESTAMP, error.getTimestamp()),
            () -> assertEquals(STATUS, error.getStatus()),
            () -> assertEquals(MESSAGE, error.getMessage()),
            () -> assertEquals(PATH, error.getPath())
        );
    }
}
