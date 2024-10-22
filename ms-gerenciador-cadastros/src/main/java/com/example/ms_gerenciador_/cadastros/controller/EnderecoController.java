package com.example.ms_gerenciador_.cadastros.controller;

import com.example.ms_gerenciador_.cadastros.dto.EnderecoRequestDTO;
import com.example.ms_gerenciador_.cadastros.model.Endereco;
import com.example.ms_gerenciador_.cadastros.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/register")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @PostMapping("/endereco")
    public ResponseEntity<Endereco> cadastrarEndereco(@RequestBody EnderecoRequestDTO endereco) {
        Endereco enderecoResponse = enderecoService.cadastrarEndereco(endereco);
        return ResponseEntity.created(
                        ServletUriComponentsBuilder
                                .fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(enderecoResponse.getId())
                                .toUri())
                .body(enderecoResponse);
    }

    @GetMapping("/endereco/{id}")
    public ResponseEntity<Endereco> buscarEnderecoPorId(@PathVariable Long id) {
        Endereco endereco = enderecoService.buscarEnderecoPorId(id);
        return ResponseEntity.ok(endereco);
    }
}
