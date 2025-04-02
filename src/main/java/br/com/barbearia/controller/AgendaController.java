package br.com.barbearia.controller;

import br.com.barbearia.dtos.AgendaDto;
import br.com.barbearia.models.Agenda;
import br.com.barbearia.services.AgendaService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/agendas")
public class AgendaController {

    @Autowired
    AgendaService agendaService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/{agendaId}")
    public ResponseEntity<AgendaDto> findById(@PathVariable Long agendaId){
        Agenda agenda = agendaService.findById(agendaId);
        return ResponseEntity.ok().body(modelMapper.map(agenda, AgendaDto.class));
    }
    @GetMapping("/listarAgendas")
    public ResponseEntity<List<AgendaDto>> findAll(){
        List<Agenda> agendas = agendaService.findAll();
        List<AgendaDto> agendaDtos = agendas.stream()
                .map(agenda -> modelMapper.map(agenda, AgendaDto.class))
                .collect(Collectors.toList());
        return  ResponseEntity.ok().body(agendaDtos);
    }

    @PostMapping
    public ResponseEntity<AgendaDto> save (@Valid @RequestBody AgendaDto agendaDto){
        Agenda agenda = modelMapper.map(agendaDto, Agenda.class);
        Agenda agendaSalva = agendaService.save(agenda);
        return ResponseEntity.ok().body(modelMapper.map(agendaSalva, AgendaDto.class));

    }

    @PutMapping("/{agendaId}")
    public ResponseEntity<AgendaDto> update (@PathVariable Long agendaId, @Valid @RequestBody AgendaDto agendaDto){
        agendaDto.setAgendaId(agendaId);
        Agenda novosDados = modelMapper.map(agendaDto, Agenda.class);
        Agenda agendaAtualizada = agendaService.update(agendaId, novosDados);
        return ResponseEntity.ok().body(modelMapper.map(agendaAtualizada, AgendaDto.class));

    }

    @DeleteMapping("/{agendaId}")
    public ResponseEntity<Void>delete (@PathVariable Long agendaId){
        agendaService.delete(agendaId);
        return  ResponseEntity.noContent().build();
    }
}
