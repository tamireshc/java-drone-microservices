package com.example.ms_gerenciador_pedidos.service;

import com.example.ms_gerenciador_pedidos.client.MSCadastroResourceClient;
import com.example.ms_gerenciador_pedidos.dto.PedidoResponseDTO;
import com.example.ms_gerenciador_pedidos.dto.UsuarioResponseDTO;
import com.example.ms_gerenciador_pedidos.dto.UsuarioResponseEnderecoDTO;
import com.example.ms_gerenciador_pedidos.exceptions.ServicoIndisponivelException;
import com.example.ms_gerenciador_pedidos.exceptions.UsuarioNaoExistenteException;
import com.example.ms_gerenciador_pedidos.model.Pedido;
import com.example.ms_gerenciador_pedidos.repository.PedidoRepository;
import feign.FeignException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
    public PedidoResponseDTO cadastrarPedido(Pedido pedido) {
        try {
            UsuarioResponseEnderecoDTO remetente = cadastroResourceClient.buscarUsuarioPorId(pedido.getUsuarioId()).getBody();
            UsuarioResponseEnderecoDTO destinatario = cadastroResourceClient.buscarUsuarioPorId(pedido.getDestinatarioId()).getBody();

            Pedido pedidoResponse = pedidoRepository.save(pedido);

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

            return pedidoResponseDTO;

        } catch (FeignException.NotFound exception) {
            throw new UsuarioNaoExistenteException("Usuario não encontrado");
        }catch (FeignException e){
            throw new ServicoIndisponivelException("Serviço ms-gerenciador-cadastros indisponível");
        }
    }

//    private Long id;
//    private LocalDateTime dataPedido;
//    private LocalDateTime dataEntrega;
//    private String status;
//    private EnderecoResponseDTO endereco;
//    private UsuarioResponseDTO usuario;
//    private UsuarioResponseDTO destinatario;
//    private Long droneId;

}
