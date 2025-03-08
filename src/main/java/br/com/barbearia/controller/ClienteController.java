package br.com.barbearia.controller;

import br.com.barbearia.models.Cliente;
import br.com.barbearia.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/clientes")
public class ClienteController {

  @Autowired
   public ClienteService clienteService;

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable Long id) {
        try {
            Cliente cliente = clienteService.findById(id);
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public ResponseEntity<Cliente>cadastrarCliente(@RequestBody Cliente cliente){
        try{
            Cliente novoCliente = clienteService.cadastrarCliente(cliente);
            return  new ResponseEntity<>(novoCliente, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/clienteNome")
    public ResponseEntity<List<Cliente>>pesquisarPorNome(@RequestParam String nome){
        List<Cliente> clientes = clienteService.pesquisarPorNome(nome);
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cliente>deleteCliente(@PathVariable Long id){
        try{
            Cliente clienteDeletado = clienteService.delete(id);
            return new ResponseEntity<>(clienteDeletado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente){
        try{
            cliente.setId(id);
            Cliente clienteAtualizado = clienteService.save(cliente);
            return new ResponseEntity<>(clienteAtualizado, HttpStatus.OK);
        } catch (Exception e) {
            return  new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping
    public ResponseEntity<List<Cliente>> pesquisarClientes() {
        List<Cliente> clientes = clienteService.pesquisarClientes();
        if(clientes.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

}
