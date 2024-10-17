package com.example.ms_gerenciador_.cadastros.controller;

import com.example.ms_gerenciador_.cadastros.dto.UsuarioResponseDTO;
import com.example.ms_gerenciador_.cadastros.dto.UsuarioResponseEnderecoDTO;
import com.example.ms_gerenciador_.cadastros.model.Usuario;
import com.example.ms_gerenciador_.cadastros.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/register")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/user")
    public ResponseEntity<UsuarioResponseDTO> cadastrarUsuario(@RequestBody Usuario usuario) throws NoSuchAlgorithmException {

        UsuarioResponseDTO novoUsuario = usuarioService.cadastrarUsuario(usuario);
        return ResponseEntity.created(
                        ServletUriComponentsBuilder
                                .fromCurrentRequest()
                                .path("/{id}").
                                buildAndExpand(novoUsuario.getId())
                                .toUri())
                .body(novoUsuario);
    }

    @GetMapping("/user/cpf/{cpf}")
    public ResponseEntity<UsuarioResponseEnderecoDTO> buscarUsuarioPorCPF(@PathVariable String cpf) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorCPF(cpf));
    }

    @GetMapping("/user/id/{id}")
    public ResponseEntity<UsuarioResponseEnderecoDTO> buscarUsuarioPorId(@PathVariable String id) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorId(id));
    }

    @GetMapping("/user")
    public ResponseEntity<List<UsuarioResponseEnderecoDTO>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable String id, @RequestBody Usuario usuario) throws NoSuchAlgorithmException {
        UsuarioResponseDTO usuarioResponse = usuarioService.editarUsuario(id, usuario);
        return ResponseEntity.ok(usuarioResponse);
    }

}
