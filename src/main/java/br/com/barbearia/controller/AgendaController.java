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

@RestController
@RequestMapping("/agendamentos")
public class AgendaController {

    @Autowired
    public AgendaService agendaService;

   @Autowired
    public ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<AgendaDto> criarAgenda(@Valid @RequestBody AgendaDto agendaDto){

        Agenda agenda = modelMapper.map(agendaDto, Agenda.class);
        Agenda novaAgenda =agendaService.save(agenda);

        // Converte a entidade salva para o DTO e retorna
        AgendaDto resposta = modelMapper.map(novaAgenda, AgendaDto.class);
        return  ResponseEntity.ok(resposta);
    }
    @GetMapping("/datas")
    public ResponseEntity<List<String>> buscarDatasAgendadas(){
        List<String> datas = agendaService.buscarPorTodasDatasAgendadas();
        return ResponseEntity.ok(datas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgendaDto> atualizarAgenda(@PathVariable Long agendaId,
                                                     @Valid @RequestBody AgendaDto agendaDto){
        Agenda agenda = modelMapper.map(agendaDto, Agenda.class);

        Agenda agendaAtualizada = agendaService.atualizarAgenda(agendaId, agenda);

        AgendaDto resposta = modelMapper.map(agendaAtualizada, AgendaDto.class);
        return  ResponseEntity.ok(resposta);

    }
    //excluir uma agenda específica por barbeiro e horário

    @DeleteMapping("/barbeiro")
    public ResponseEntity<String> excluirAgendamentoDoBarbeiro(@RequestParam Long barbeiroId,
                                      @RequestParam String dataHora){
        agendaService.excluirAgendamentoDoBarbeiro(barbeiroId, dataHora);
        return ResponseEntity.ok("Agendamento para o barbeiro no horário "
                + dataHora + " foi excluído com sucesso.");
    }

    @DeleteMapping("/cliente")
    public ResponseEntity<String> excluirAgendamentoDoCliente(@RequestParam Long clienteId,
                                                              @RequestParam String dataHora){
        agendaService.excluirAgendamentoDoCliente(clienteId, dataHora);
        return ResponseEntity.ok("Agendamento para o cliente no horário "
                + dataHora + " foi excluído com sucesso.");
    }

}
