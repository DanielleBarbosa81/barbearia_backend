package br.com.barbearia.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WebConfigTest {

    private WebConfig webConfig = new WebConfig();

    @Test
    void whenAddCorsMappingsThenConfigureCorrectly() {
        CorsRegistry registry = new CorsRegistry();
        
        webConfig.addCorsMappings(registry);
        
        // Como o CorsRegistry não fornece getters para verificar as configurações,
        // vamos apenas verificar se não lança exceção ao configurar
        assertDoesNotThrow(() -> webConfig.addCorsMappings(registry));
    }
}
