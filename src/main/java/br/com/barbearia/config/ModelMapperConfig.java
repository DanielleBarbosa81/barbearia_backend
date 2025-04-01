package br.com.barbearia.config;

import br.com.barbearia.dtos.AgendaDto;
import br.com.barbearia.dtos.ClienteDto;
import br.com.barbearia.models.Agenda;
import br.com.barbearia.models.Barbeiro;
import br.com.barbearia.models.Cliente;
import br.com.barbearia.repository.BarbeiroRepository;
import br.com.barbearia.repository.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BarbeiroRepository barbeiroRepository;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.STRICT)
            .setSkipNullEnabled(true);
        
        modelMapper.typeMap(AgendaDto.class, Agenda.class)
            .addMappings(mapper -> {
                mapper.skip(Agenda::setAgendaId);
            })
            .setPostConverter(context -> {
                AgendaDto source = context.getSource();
                Agenda destination = context.getDestination();

                if (source.getClienteId() != null) {
                    Cliente cliente = clienteRepository.findById(source.getClienteId())
                        .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
                    destination.setCliente(cliente);
                }

                if (source.getBarbeiroId() != null) {
                    Barbeiro barbeiro = barbeiroRepository.findById(source.getBarbeiroId())
                        .orElseThrow(() -> new RuntimeException("Barbeiro não encontrado"));
                    destination.setBarbeiro(barbeiro);
                }

                return destination;
            });

        modelMapper.typeMap(Agenda.class, AgendaDto.class)
            .addMappings(mapper -> {
                mapper.map(src -> src.getCliente().getClienteId(), AgendaDto::setClienteId);
                mapper.map(src -> src.getBarbeiro().getBarbeiroId(), AgendaDto::setBarbeiroId);
            });

        return modelMapper;
    }
}
