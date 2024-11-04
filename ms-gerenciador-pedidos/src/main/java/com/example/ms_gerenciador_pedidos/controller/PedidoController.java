package com.example.ms_gerenciador_pedidos.controller;

import com.example.ms_gerenciador_pedidos.dto.PedidoRequestDTO;
import com.example.ms_gerenciador_pedidos.dto.PedidoResponseDTO;
import com.example.ms_gerenciador_pedidos.model.Pedido;
import com.example.ms_gerenciador_pedidos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/order")
public class PedidoController {
    @Autowired
    PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> cadastrarPedido(@RequestBody PedidoRequestDTO pedido) {
        PedidoResponseDTO pedidoResponseDTO = pedidoService.cadastrarPedido(pedido);
        return ResponseEntity.created(
                        ServletUriComponentsBuilder
                                .fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(pedidoResponseDTO.getId())
                                .toUri())
                .body(pedidoResponseDTO);
    }
}
