package br.com.barbearia.config;

import br.com.barbearia.dtos.ClienteDto;
import br.com.barbearia.models.Cliente;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {


    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<Cliente, ClienteDto>() {
            @Override
            protected void configure() {
                map(source.getClienteId(), destination.getClienteId()); // Mapeia o ID
            }
        });

        return modelMapper;

    }

}
