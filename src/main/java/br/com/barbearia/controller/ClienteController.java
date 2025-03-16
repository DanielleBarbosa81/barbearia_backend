package br.com.barbearia.controller;


import br.com.barbearia.dtos.BarbeiroDto;
import br.com.barbearia.dtos.ClienteDto;
import br.com.barbearia.models.Cliente;
import br.com.barbearia.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/clientes")
public class ClienteController {

  @Autowired
   public ClienteService clienteService;

  @Autowired
  public ModelMapper modelMapper;


    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> findById (@PathVariable Long clienteId){
        Cliente cliente = clienteService.findById(clienteId);
        return ResponseEntity.ok().body(modelMapper.map(cliente, ClienteDto.class));
    }
    @GetMapping
    public ResponseEntity<List<ClienteDto>> findAll (){
        List<Cliente> clientes = clienteService.findAll();

        List<ClienteDto> clienteDtos = clientes.stream().map(cliente -> modelMapper.map(cliente, ClienteDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(clienteDtos);

    }
    @PostMapping
    public ResponseEntity<ClienteDto> save(@Valid @RequestBody ClienteDto clienteDto) {
        // Converte o DTO para a entidade
        Cliente cliente = modelMapper.map(clienteDto, Cliente.class);

        // Salva o cliente no banco
        Cliente clienteSalvo = clienteService.save(cliente);

        // Converte a entidade salva de volta para o DTO
        ClienteDto resposta = modelMapper.map(clienteSalvo, ClienteDto.class);

        // Retorna a resposta
        return ResponseEntity.ok().body(resposta);
    }


    @PutMapping
    public ResponseEntity<ClienteDto> update (@PathVariable Long clienteId, @Valid @RequestBody ClienteDto clienteDto){
        clienteDto.setClienteId(clienteId);

        Cliente novosDados = modelMapper.map(clienteDto, Cliente.class);

        Cliente clienteAtualizado = clienteService.update(clienteId, novosDados);

        ClienteDto resposta = modelMapper.map(clienteAtualizado, ClienteDto.class);
        return ResponseEntity.ok(resposta);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long clienteId){
        clienteService.delete(clienteId);
        return ResponseEntity.noContent().build();
    }


}
