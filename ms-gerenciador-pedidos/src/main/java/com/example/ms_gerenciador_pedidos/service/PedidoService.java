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
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.net.UnknownHostException;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    MSCadastroResourceClient cadastroResourceClient;

    @Transactional
    public PedidoResponseDTO cadastrarPedido(PedidoRequestDTO pedidoRequestDTO) {

        if (!StatusPedido.equals(pedidoRequestDTO.getStatus())) {
            throw new StatusInvalidoException("Status inexistente");
        }

        try {
            UsuarioResponseEnderecoDTO remetente = cadastroResourceClient.buscarUsuarioPorId(pedidoRequestDTO.getUsuarioId()).getBody();
            UsuarioResponseEnderecoDTO destinatario = cadastroResourceClient.buscarUsuarioPorId(pedidoRequestDTO.getDestinatarioId()).getBody();
            EnderecoDTO endereco = cadastroResourceClient.buscarEnderecoPorId(pedidoRequestDTO.getEnderecoId()).getBody();

            Pedido pedidoResponse = pedidoRepository.save(pedidoRequestDTO.toPedido(pedidoRequestDTO));

            PedidoResponseDTO pedidoResponseDTO = new PedidoResponseDTO();
            pedidoResponseDTO.setId(pedidoResponse.getId());
            pedidoResponseDTO.setDataPedido(pedidoResponse.getDataPedido());
            pedidoResponseDTO.setStatus(pedidoResponse.getStatus().toString());
            pedidoResponseDTO.setRemetente(
                    new UsuarioResponseDTO(remetente)
                            .converterUsuarioResponseEnderecoDTOparaUsuarioResponseDTO(remetente));
            pedidoResponseDTO.setDestinatario(
                    new UsuarioResponseDTO(destinatario)
                            .converterUsuarioResponseEnderecoDTOparaUsuarioResponseDTO(destinatario));
//            assert endereco != null;
            pedidoResponseDTO.setEndereco(new EnderecoDTO().toEnderecoResponseDTO(endereco));

            return pedidoResponseDTO;

        } catch (FeignException.NotFound exception) {
            throw new UsuarioNaoExistenteException("Remetente, destinatário ou endereço não cadastrado");
        } catch (FeignException e) {
            throw new ServicoIndisponivelException("Serviço ms-gerenciador-cadastros indisponível");
        }
    }
}
