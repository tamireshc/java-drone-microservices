package com.example.ms_gerenciador_pedidos.client;

import com.example.ms_gerenciador_pedidos.dto.UsuarioResponseEnderecoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(
        name = "ms-gerenciador-cadastros",
        path = "/register"
)
public interface MSCadastroResourceClient {
    @GetMapping("/user/id/{id}")
    ResponseEntity<UsuarioResponseEnderecoDTO> buscarUsuarioPorId(@PathVariable Long id);
}