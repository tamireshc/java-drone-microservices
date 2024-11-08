
package com.example.ms_gerenciador_pedidos.service;

import com.example.ms_gerenciador_pedidos.client.MSCadastroResourceClient;
import com.example.ms_gerenciador_pedidos.dto.*;
import com.example.ms_gerenciador_pedidos.exceptions.*;
import com.example.ms_gerenciador_pedidos.model.Pedido;
import com.example.ms_gerenciador_pedidos.model.enuns.StatusPedido;
import com.example.ms_gerenciador_pedidos.repository.PedidoRepository;
import feign.FeignException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    BuscarRemetenteDestinatarioEnderecoService buscarRemetenteDestinatarioEnderecoService;
    @Autowired
    private MSCadastroResourceClient cadastroResourceClient;
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
        System.out.println(pedido.getUsuarioId());
        System.out.println(pedido.getDestinatarioId());
        System.out.println(id);

        RemetenteDestinatarioEnderecoDTO remetenteDestinatarioEnderecoDTO = buscarRemetenteDestinatarioEnderecoService
                .busca(pedido.getUsuarioId(), pedido.getDestinatarioId(), pedido.getEnderecoId());

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

    public List<Pedido> buscarPedidosPorUsuarioId(Long id) {
        List<Pedido> pedidos = pedidoRepository.findByUsuarioId(id);
        return pedidos;
    }

    public void deletarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id).orElse(null);
        if (pedido == null) {
            throw new PedidoInexistenteException("Pedido não encontrado");
        }
        if (pedido.getStatus() != StatusPedido.CRIADO) {
            throw new OperacaoInvalidaException("Pedido não pode ser deletado");
        }
        pedidoRepository.deleteById(id);
    }

    @Transactional
    public PedidoResponseDTO editarPedido(Long id, PedidoRequestDTO pedido) {
        Pedido pedidoExistente = pedidoRepository.findById(id).orElse(null);
        if (pedidoExistente == null) {
            throw new PedidoInexistenteException("Pedido não encontrado");
        }
        if (!StatusPedido.equals(pedido.getStatus())) {
            throw new StatusInvalidoException("Status inexistente");
        }
        //Verificando a existencia do remetente, destinatário e endereço
        RemetenteDestinatarioEnderecoDTO remetenteDestinatarioEnderecoDTO = buscarRemetenteDestinatarioEnderecoService
                .busca(pedido.getUsuarioId(),
                        pedido.getDestinatarioId(),
                        pedido.getEnderecoId());
        //Verificando a existência do drone
        try {
            DroneDTO drone = cadastroResourceClient.buscarDronePorId(pedido.getDroneId()).getBody();
            if (drone == null) {
                throw new PedidoInexistenteException("Drone não encontrado");
            }
            if (pedidoExistente.getStatus().equals("CRIADO") && !Objects.equals(pedido.getDroneId(), pedidoExistente.getDroneId())
                    && !drone.getStatus().toUpperCase().equals("DISPONIVEL")) {
                throw new OperacaoInvalidaException("Não é possível alocar este drone para este pedido");
            }
        } catch (FeignException e) {
            System.out.println(e.getMessage());
            throw new ServicoIndisponivelException("Serviço ms-gerenciador-cadastros indisponível");
        }

        if ((pedido.getStatus().equalsIgnoreCase("EM_ROTA") || pedido.getStatus().equalsIgnoreCase("ENTREGUE")
                || pedido.getStatus().equalsIgnoreCase("CANCELADO"))
                && pedido.getDroneId() != pedidoExistente.getDroneId()) {
            throw new OperacaoInvalidaException("Não é possível alterar o drone deste pedido");
        }
        //Salvando o pedido editado no banco de dados
        pedidoExistente.setUsuarioId(pedido.getUsuarioId());
        pedidoExistente.setDestinatarioId(pedido.getDestinatarioId());
        pedidoExistente.setEnderecoId(pedido.getEnderecoId());
        pedidoExistente.setStatus(StatusPedido.valueOf(pedido.getStatus().toUpperCase()));
        pedidoExistente.setDroneId(pedido.getDroneId());
        Pedido pedidoEditado = pedidoRepository.save(pedidoExistente);
        //Convertendo a resposta para o DTO
        PedidoResponseDTO pedidoResponseDTO = new PedidoResponseDTO();
        pedidoResponseDTO.setId(id);
        pedidoResponseDTO.setDataPedido(pedidoEditado.getDataPedido());
        pedidoResponseDTO.setDataEntrega(pedidoEditado.getDataEntrega());
        pedidoResponseDTO.setStatus(pedidoEditado.getStatus().toString());
        pedidoResponseDTO.setRemetente(remetenteDestinatarioEnderecoDTO.getRemetente());
        pedidoResponseDTO.setDestinatario(remetenteDestinatarioEnderecoDTO.getDestinatario());
        pedidoResponseDTO.setEndereco(remetenteDestinatarioEnderecoDTO.getEndereco());
        pedidoResponseDTO.setDroneId(pedidoEditado.getDroneId());

        return pedidoResponseDTO;
    }
}
