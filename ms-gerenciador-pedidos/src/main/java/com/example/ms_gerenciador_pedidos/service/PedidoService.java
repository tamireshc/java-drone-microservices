
package com.example.ms_gerenciador_pedidos.service;

import com.example.ms_gerenciador_pedidos.dto.*;
import com.example.ms_gerenciador_pedidos.exceptions.OperacaoInvalidaException;
import com.example.ms_gerenciador_pedidos.exceptions.PedidoInexistenteException;
import com.example.ms_gerenciador_pedidos.exceptions.StatusInvalidoException;
import com.example.ms_gerenciador_pedidos.model.Pedido;
import com.example.ms_gerenciador_pedidos.model.enuns.StatusPedido;
import com.example.ms_gerenciador_pedidos.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    BuscarRemetenteDestinatarioEnderecoService buscarRemetenteDestinatarioEnderecoService;
    @Autowired
    private EnviarParaFilaService enviarParaFilaService;
    @Value("${rabbitmq.dronependente.exchange}")
    private String enchangedDronePendente;

    @Transactional
    public PedidoResponseDTO cadastrarPedido(PedidoRequestDTO pedidoRequestDTO) {
        if (!StatusPedido.equals(pedidoRequestDTO.getStatus())) {
            throw new StatusInvalidoException("Status inexistente");
        }
        RemetenteDestinatarioEnderecoDTO remetenteDestinatarioEnderecoDTO = buscarRemetenteDestinatarioEnderecoService
                .busca(pedidoRequestDTO.getUsuarioId(),
                        pedidoRequestDTO.getDestinatarioId(),
                        pedidoRequestDTO.getEnderecoId());
        //Salvando o pedido no banco de dados
        Pedido pedidoResponse = pedidoRepository.save(pedidoRequestDTO.toPedido(pedidoRequestDTO));
        //Enviado uma solicitação para incluir um drone ao pedido para fila
        enviarParaFilaService.enviarBuscaDeDroneDisponivelParaFila(pedidoResponse.getId(), enchangedDronePendente);

        PedidoResponseDTO pedidoResponseDTO =
                new PedidoResponseDTO(pedidoResponse.getId(),
                        pedidoResponse.getDataPedido(),
                        pedidoResponse.getStatus().toString(),
                        remetenteDestinatarioEnderecoDTO.getEndereco(),
                        remetenteDestinatarioEnderecoDTO.getRemetente(),
                        remetenteDestinatarioEnderecoDTO.getDestinatario());
        return pedidoResponseDTO;
    }

    public PedidoResponseDTO buscarPedidoPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id).orElse(null);
        if (pedido == null) {
            throw new PedidoInexistenteException("Pedido não encontrado");
        }
        RemetenteDestinatarioEnderecoDTO remetenteDestinatarioEnderecoDTO = buscarRemetenteDestinatarioEnderecoService
                .busca(pedido.getUsuarioId(), pedido.getDestinatarioId(), id);

        PedidoResponseDTO pedidoResponseDTO = new PedidoResponseDTO();
        pedidoResponseDTO.setId(pedido.getId());
        pedidoResponseDTO.setDataPedido(pedido.getDataPedido());
        pedidoResponseDTO.setStatus(pedido.getStatus().toString());
        pedidoResponseDTO.setEndereco(remetenteDestinatarioEnderecoDTO.getEndereco());
        pedidoResponseDTO.setRemetente(remetenteDestinatarioEnderecoDTO.getRemetente());
        pedidoResponseDTO.setDestinatario(remetenteDestinatarioEnderecoDTO.getDestinatario());
        pedidoResponseDTO.setDroneId(pedido.getDroneId());

        return pedidoResponseDTO;
    }
    public List<Pedido>buscarPedidoPorUsuarioId(Long id) {
        List<Pedido> pedido = pedidoRepository.findByUsuarioId(id);
        return pedido;
    }

    public void deletarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id).orElse(null);
        if (pedido == null) {
            throw new PedidoInexistenteException("Pedido não encontrado");
        }
        if(pedido.getStatus() != StatusPedido.CRIADO) {
            throw new OperacaoInvalidaException("Pedido não pode ser deletado");
        }
        pedidoRepository.deleteById(id);
    }
}
