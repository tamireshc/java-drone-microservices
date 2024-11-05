package com.example.ms_gerenciador_pedidos.service;

import com.example.ms_gerenciador_pedidos.client.MSCadastroResourceClient;
import com.example.ms_gerenciador_pedidos.dto.*;
import com.example.ms_gerenciador_pedidos.exceptions.ServicoIndisponivelException;
import com.example.ms_gerenciador_pedidos.exceptions.StatusInvalidoException;
import com.example.ms_gerenciador_pedidos.exceptions.UsuarioNaoExistenteException;
import com.example.ms_gerenciador_pedidos.model.Pedido;
import com.example.ms_gerenciador_pedidos.model.enuns.StatusPedido;
import com.example.ms_gerenciador_pedidos.repository.PedidoRepository;
import feign.FeignException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    MSCadastroResourceClient cadastroResourceClient;
    @Autowired
    private EnviarParaFilaService enviarParaFilaService;
    @Value("${rabbitmq.dronependente.exchange}")
    private String enchangedDronePendente;

    @Transactional
    public PedidoResponseDTO cadastrarPedido(PedidoRequestDTO pedidoRequestDTO) {
        if (!StatusPedido.equals(pedidoRequestDTO.getStatus())) {
            throw new StatusInvalidoException("Status inexistente");
        }
        try {
            UsuarioResponseEnderecoDTO remetente = cadastroResourceClient.buscarUsuarioPorId(pedidoRequestDTO.getUsuarioId()).getBody();
            UsuarioResponseEnderecoDTO destinatario = cadastroResourceClient.buscarUsuarioPorId(pedidoRequestDTO.getDestinatarioId()).getBody();
            EnderecoDTO endereco = cadastroResourceClient.buscarEnderecoPorId(pedidoRequestDTO.getEnderecoId()).getBody();
            //Salvando o pedido no banco de dados
            Pedido pedidoResponse = pedidoRepository.save(pedidoRequestDTO.toPedido(pedidoRequestDTO));
            //Enviado uma solicitação para incluir um drone ao pedido para fila
            enviarParaFilaService.enviarBuscaDeDroneDisponivelParaFila(pedidoResponse.getId(), enchangedDronePendente);

            PedidoResponseDTO pedidoResponseDTO =
                    new PedidoResponseDTO(pedidoResponse.getId(),
                            pedidoResponse.getDataPedido(),
                            pedidoResponse.getStatus().toString(),
                            new EnderecoDTO().toEnderecoResponseDTO(endereco),
                            new UsuarioResponseDTO(remetente)
                                    .converterUsuarioResponseEnderecoDTOparaUsuarioResponseDTO(remetente),
                            new UsuarioResponseDTO(destinatario)
                                    .converterUsuarioResponseEnderecoDTOparaUsuarioResponseDTO(destinatario)
                    );
            return pedidoResponseDTO;
        } catch (FeignException.NotFound exception) {
            throw new UsuarioNaoExistenteException("Remetente, destinatário ou endereço não cadastrado");
        } catch (FeignException e) {
            throw new ServicoIndisponivelException("Serviço ms-gerenciador-cadastros indisponível");
        }
    }
}
