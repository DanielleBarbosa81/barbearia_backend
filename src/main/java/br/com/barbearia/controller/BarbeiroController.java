package br.com.barbearia.controller;

import br.com.barbearia.dtos.BarbeiroDto;
import br.com.barbearia.models.Barbeiro;
import br.com.barbearia.services.BarbeiroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;


import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/barbeiros")
public class BarbeiroController {

    @Autowired
    public BarbeiroService barbeiroService;


    @Autowired
    public ModelMapper modelMapper;

    @GetMapping("/{barbeiroid}")
    public ResponseEntity<BarbeiroDto> findById (@PathVariable Long id){
        Barbeiro barbeiro = barbeiroService.findById(id);
        return ResponseEntity.ok().body(modelMapper.map(barbeiro, BarbeiroDto.class));
    }

    @GetMapping
    public ResponseEntity<List<BarbeiroDto>> findAll(@RequestParam (value = "agenda", defaultValue = "0") Long id){
        List<Barbeiro> barbeiros = barbeiroService.findAll();
        List<BarbeiroDto> barbeiroDtos = barbeiros.stream()
                .map(barbeiro -> modelMapper.map(barbeiro, BarbeiroDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(barbeiroDtos);
    }

    @PostMapping
    public ResponseEntity<BarbeiroDto> save (@Valid @RequestBody BarbeiroDto barbeiroDto){
        Barbeiro barbeiro = modelMapper.map(barbeiroDto, Barbeiro.class);
        Barbeiro barb = barbeiroService.save(barbeiro);
        return ResponseEntity.ok().body(modelMapper.map(barb, BarbeiroDto.class));

    }

    @PutMapping("/{id}")
    public ResponseEntity<BarbeiroDto> update(@PathVariable Long id, @Valid @RequestBody BarbeiroDto barbeiroDto) {

        barbeiroDto.setId(id); // Mapeia o ID para o DTO

        // Mapeando os dados recebidos (DTO para Entidade)
        Barbeiro novosDados = modelMapper.map(barbeiroDto, Barbeiro.class);

        // Atualizando o barbeiro através do serviço
        Barbeiro barbeiroAtualizado = barbeiroService.update(id, novosDados);

        // Convertendo a entidade atualizada para DTO e retornando na resposta
        BarbeiroDto resposta = modelMapper.map(barbeiroAtualizado, BarbeiroDto.class);

        return ResponseEntity.ok(resposta);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id){
        barbeiroService.delete(id);
        return ResponseEntity.noContent().build();
    }

}