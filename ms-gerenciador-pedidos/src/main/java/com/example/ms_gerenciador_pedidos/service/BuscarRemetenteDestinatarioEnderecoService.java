package com.example.ms_gerenciador_pedidos.service;

import com.example.ms_gerenciador_pedidos.client.MSCadastroResourceClient;
import com.example.ms_gerenciador_pedidos.dto.EnderecoDTO;
import com.example.ms_gerenciador_pedidos.dto.RemetenteDestinatarioEnderecoDTO;
import com.example.ms_gerenciador_pedidos.dto.UsuarioResponseDTO;
import com.example.ms_gerenciador_pedidos.dto.UsuarioResponseEnderecoDTO;
import com.example.ms_gerenciador_pedidos.exceptions.ServicoIndisponivelException;
import com.example.ms_gerenciador_pedidos.exceptions.UsuarioNaoExistenteException;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuscarRemetenteDestinatarioEnderecoService {
    @Autowired
    private MSCadastroResourceClient cadastroResourceClient;

    public RemetenteDestinatarioEnderecoDTO busca(Long remetenteId, Long destinatarioId, Long enderecoId) {
        try {
            UsuarioResponseEnderecoDTO remetente = cadastroResourceClient
                    .buscarUsuarioPorId(remetenteId).getBody();
            UsuarioResponseEnderecoDTO destinatario = cadastroResourceClient
                    .buscarUsuarioPorId(destinatarioId).getBody();
            EnderecoDTO endereco = cadastroResourceClient
                    .buscarEnderecoPorId(enderecoId).getBody();

            RemetenteDestinatarioEnderecoDTO remetenteDestinatarioEnderecoDTO = new RemetenteDestinatarioEnderecoDTO();

            remetenteDestinatarioEnderecoDTO.setRemetente(new UsuarioResponseDTO(remetente)
                    .converterUsuarioResponseEnderecoDTOparaUsuarioResponseDTO(remetente));
            remetenteDestinatarioEnderecoDTO.setDestinatario(new UsuarioResponseDTO(destinatario)
                    .converterUsuarioResponseEnderecoDTOparaUsuarioResponseDTO(destinatario));
            remetenteDestinatarioEnderecoDTO.setEndereco(endereco.toEnderecoResponseDTO(endereco));

            return remetenteDestinatarioEnderecoDTO;
        } catch (FeignException.NotFound exception) {
            throw new UsuarioNaoExistenteException("Remetente, destinatário ou endereço não cadastrado");
        } catch (FeignException e) {
            throw new ServicoIndisponivelException("Serviço ms-gerenciador-cadastros indisponível");
        }
    }
}
