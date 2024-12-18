package com.example.ms_gerenciador_.cadastros.controller;

import com.example.ms_gerenciador_.cadastros.dto.EnderecoRequestDTO;
import com.example.ms_gerenciador_.cadastros.model.Endereco;
import com.example.ms_gerenciador_.cadastros.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/register")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @PostMapping("/address")
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

    @GetMapping("/address/{id}")
    public ResponseEntity<Endereco> buscarEnderecoPorId(@PathVariable Long id) {
        Endereco endereco = enderecoService.buscarEnderecoPorId(id);
        return ResponseEntity.ok(endereco);
    }

    @GetMapping("/address/user/{id}")
    public ResponseEntity<List<Endereco>> buscarEnderecosPorUsuarioId(@PathVariable Long id) {
        return ResponseEntity.ok(enderecoService.buscarEnderecosPorUsuarioId(id));
    }

    @GetMapping("/address")
    public ResponseEntity<List<Endereco>> listarEnderecos() {
        return ResponseEntity.ok(enderecoService.listarEnderecos());
    }

    @PutMapping("/address/{id}")
    public ResponseEntity<Endereco> editarEndereco(@PathVariable Long id, @RequestBody EnderecoRequestDTO endereco) {
        Endereco enderecoResponse = enderecoService.editarEnderecoFETCHAPI(id, endereco);
        return ResponseEntity.ok(enderecoResponse);
    }

    @DeleteMapping("/address/{id}")
    public ResponseEntity<String> deletarEndereco(@PathVariable Long id) {
        enderecoService.deletarEndereco(id);
        return ResponseEntity.ok("Endereço deletado com sucesso");
    }
}
