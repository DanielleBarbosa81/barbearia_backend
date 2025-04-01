package br.com.barbearia.config;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ModelMapperConfigTest {

    private ModelMapperConfig modelMapperConfig = new ModelMapperConfig();

    @Test
    void whenModelMapperThenReturnNotNull() {
        ModelMapper modelMapper = modelMapperConfig.modelMapper();
        assertNotNull(modelMapper);
    }

    @Test
    void whenModelMapperThenReturnModelMapperInstance() {
        ModelMapper modelMapper = modelMapperConfig.modelMapper();
        assertEquals(ModelMapper.class, modelMapper.getClass());
    }
}
