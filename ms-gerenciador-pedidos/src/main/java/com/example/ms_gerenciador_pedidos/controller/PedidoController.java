package com.example.ms_gerenciador_pedidos.controller;

import com.example.ms_gerenciador_pedidos.dto.PedidoRequestDTO;
import com.example.ms_gerenciador_pedidos.dto.PedidoResponseDTO;
import com.example.ms_gerenciador_pedidos.model.Pedido;
import com.example.ms_gerenciador_pedidos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

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

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPedidoPorId(@PathVariable Long id) {
        PedidoResponseDTO pedidoResponseDTO = pedidoService.buscarPedidoPorId(id);
        return ResponseEntity.ok(pedidoResponseDTO);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity <List<Pedido>> buscarPedidoPorUsuarioId(@PathVariable Long id) {
        List<Pedido> pedidos = pedidoService.buscarPedidosPorUsuarioId(id);
        return ResponseEntity.ok(pedidos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        pedidoService.deletarPedido(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> alterarStatusPedido(@PathVariable Long id, @RequestBody PedidoRequestDTO pedido) {
        PedidoResponseDTO pedidoResponseDTO = pedidoService.editarPedido(id, pedido);
        return ResponseEntity.ok(pedidoResponseDTO);
    }
}
