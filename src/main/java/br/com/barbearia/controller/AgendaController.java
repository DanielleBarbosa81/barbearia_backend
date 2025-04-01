package br.com.barbearia.controller;

import br.com.barbearia.dtos.AgendaDto;
import br.com.barbearia.models.Agenda;
import br.com.barbearia.services.AgendaService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/agenda")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<AgendaDto> save(@Valid @RequestBody AgendaDto agendaDto) {
        Agenda agenda = modelMapper.map(agendaDto, Agenda.class);
        Agenda novaAgenda = agendaService.save(agenda);
        AgendaDto resposta = modelMapper.map(novaAgenda, AgendaDto.class);
        return ResponseEntity.ok(resposta);
    }

@GetMapping("/listarAgendamentos")
public ResponseEntity<List<AgendaDto>> findAll() {
    List<Agenda> agendas = agendaService.findAll();
    List<AgendaDto> agendaDtos = agendas.stream().map(agenda -> modelMapper.map(agenda, AgendaDto.class))
            .collect(Collectors.toList());
    return ResponseEntity.ok().body(agendaDtos);

}

    @GetMapping("/agendamentos")
    public ResponseEntity<List<String>> buscarDatasAgendadas() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        List<String> datas = agendaService.buscarPorTodasDatasAgendadas()
                .stream()
                .map(dateTime -> dateTime.format(formatter))
                .collect(Collectors.toList());

        return ResponseEntity.ok(datas);
    }



    @PutMapping("/{agendaId}")
    public ResponseEntity<AgendaDto> atualizarAgenda(@PathVariable Long agendaId, @Valid @RequestBody AgendaDto agendaDto) {
        Agenda agenda = modelMapper.map(agendaDto, Agenda.class);
        Agenda agendaAtualizada = agendaService.atualizarAgenda(agendaId, agenda);
        AgendaDto resposta = modelMapper.map(agendaAtualizada, AgendaDto.class);
        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping("/barbeiro")
    public ResponseEntity<String> excluirAgendamentoDoBarbeiro(@RequestParam Long barbeiroId, @RequestParam String dataHora) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(dataHora, formatter);

        agendaService.excluirAgendamentoDoBarbeiro(barbeiroId, dateTime);
        return ResponseEntity.ok("Agendamento para o barbeiro no horário " + dataHora + " foi excluído com sucesso.");
    }

    @DeleteMapping("/cliente")
    public ResponseEntity<String> excluirAgendamentoDoCliente(@RequestParam Long clienteId, @RequestParam String dataHora) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(dataHora, formatter);

        agendaService.excluirAgendamentoDoCliente(clienteId, dateTime);
        return ResponseEntity.ok("Agendamento para o cliente no horário " + dataHora + " foi excluído com sucesso.");
    }
}
