package com.example.ms_gerenciador_.cadastros.controller;

import com.example.ms_gerenciador_.cadastros.dto.UsuarioResponseDTO;
import com.example.ms_gerenciador_.cadastros.model.Usuario;
import com.example.ms_gerenciador_.cadastros.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/register")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

//    @GetMapping
//    public String status() {
//        return "ok";
//    }

    @PostMapping("/user")
    public ResponseEntity<UsuarioResponseDTO> cadastrarUsuario(@RequestBody Usuario usuario) throws NoSuchAlgorithmException {

        UsuarioResponseDTO novoUsuario = usuarioService.cadastrarUsuario(usuario);

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(novoUsuario.getId()).toUri()).body(novoUsuario);
    }

    @GetMapping("/user/{cpf}")
    public ResponseEntity<?> buscarUsuarioPorCPF(@PathVariable String cpf) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorCPF(cpf));
    }

    @GetMapping("/user")
    public ResponseEntity<?> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }


}
