package br.com.barbearia.controller;

import br.com.barbearia.models.Barbeiro;
import br.com.barbearia.services.BarbeiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.util.List;

@RestController
@RequestMapping("/barbeiros")
public class BarbeiroController {

    @Autowired
    public BarbeiroService barbeiroService;

    @GetMapping("/{id}")
    public ResponseEntity<Barbeiro> findById(@PathVariable Long id) {
        try {
            Barbeiro barbeiro = barbeiroService.findById(id);
            return new ResponseEntity<>(barbeiro, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/especialidade")
    public ResponseEntity<List<Barbeiro>> pesquisarPorEspecialidade(@RequestParam String especialidade) {
        List<Barbeiro> barbeiros = barbeiroService.pesquisarPorEspecialidade(especialidade);
        if (barbeiros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(barbeiros, HttpStatus.OK);
    }

    @GetMapping("/barbeiros")
    public ResponseEntity<List<Barbeiro>> obterTodosBarbeiros() {
        List<Barbeiro> barbeiros = barbeiroService.obterTodosBarbeiros();

        if (barbeiros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(barbeiros);

    }

    @GetMapping("/barbeiroNome")
    public ResponseEntity<List<Barbeiro>> pesquisarPorNome(@RequestParam String nome) {
        try {
            List<Barbeiro> barbeiros = barbeiroService.pesquisarPorNome(nome);
            return new ResponseEntity<>(barbeiros, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Barbeiro>cadastrarBarbeiro(@RequestBody Barbeiro barbeiro){
        try{
            Barbeiro novoBarbeiro = barbeiroService.save(barbeiro);
            return new ResponseEntity<>(novoBarbeiro, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Barbeiro> atualizarBarbeiro(@PathVariable Long id, @RequestBody Barbeiro barbeiro){
        try{
            barbeiro.setId(id);
            Barbeiro barbeiroAtualizado = barbeiroService.save(barbeiro);
            return new ResponseEntity<>(barbeiroAtualizado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Barbeiro> excluirBarbeiro(@PathVariable Long id){
        boolean isDelete = barbeiroService.excluirBarbeiro(id);

        if(isDelete){
            return  ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}