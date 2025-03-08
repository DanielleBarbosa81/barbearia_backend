package br.com.barbearia.controller;

import br.com.barbearia.models.Agenda;
import br.com.barbearia.services.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
public class AgendaController {

    @Autowired
    public AgendaService agendaService;

 @PostMapping
    public ResponseEntity<Agenda> criarAgenda(@RequestBody Agenda agenda){
     try{
         Agenda agendamentoCriado = agendaService.criarAgenda(agenda);
         return ResponseEntity.ok(agendamentoCriado);
     } catch (IllegalArgumentException e) {
         return ResponseEntity.badRequest().body(null);

     }
 }

 @GetMapping("/datas")
    public ResponseEntity<List<String>> listarDatasAgendadas(){
     List<String> datasAgendadas = agendaService.buscarPorTodasDatasAgendadas();
     return ResponseEntity.ok(datasAgendadas);
 }
 @PutMapping("/{id}")
    public ResponseEntity<?> atualizarAgenda(@PathVariable Long id, @RequestBody Agenda agenda){
     try{
         Agenda agendamentoAtualizado = agendaService.atualizarAgenda(id, agenda);
         return ResponseEntity.ok(agendamentoAtualizado);
     } catch (IllegalArgumentException e) {
         return ResponseEntity.badRequest().body(e.getMessage());

     }
 }

   @DeleteMapping("/barbeiro/{barbeiroId}/horario")
    public ResponseEntity<?> excluirAgendamentoDoBarbeiro(@PathVariable Long barbeiroId, @RequestParam String dataHora){
     try{
         agendaService.excluirAgendamentoDoBarbeiro(barbeiroId, dataHora);
         return ResponseEntity.noContent().build();
     } catch (IllegalArgumentException e) {
         return ResponseEntity.badRequest().body(e.getMessage());
     }
   }
}
