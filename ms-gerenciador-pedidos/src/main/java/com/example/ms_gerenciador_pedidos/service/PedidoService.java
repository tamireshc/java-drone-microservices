
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
    @Value("${rabbitmq.novomonitoramento.exchange}")
    private String enchangedNovoMonitoramento;
    @Value("${rabbitmq.notificador.exchange}")
    private String enchangedNotificador;

    @Transactional
    public PedidoResponseDTO cadastrarPedido(PedidoRequestDTO pedidoRequestDTO) {
        if (!StatusPedido.equals(pedidoRequestDTO.getStatus())) {
            throw new StatusInvalidoException("Status inexistente");
        }
        RemetenteDestinatarioEnderecoDTO remetenteDestinatarioEnderecoDTO = buscarRemetenteDestinatarioEnderecoService
                .busca(pedidoRequestDTO.getRemetenteId(),
                        pedidoRequestDTO.getDestinatarioId(),
                        pedidoRequestDTO.getEnderecoId());
        //Salvando o pedido no banco de dados
        Pedido pedidoResponse = pedidoRepository.save(pedidoRequestDTO.toPedido(pedidoRequestDTO));
        //Enviado uma solicitação para incluir um drone ao pedido para fila
        enviarParaFilaService.enviarBuscaDeDroneDisponivelParaFila(pedidoResponse.getId(), enchangedDronePendente);
        //Enviando uma notificação para a fila
        DadosPedidoDTO dadosPedidoDTORemetente = new DadosPedidoDTO();
        dadosPedidoDTORemetente.setPedidoId(pedidoResponse.getId());
        dadosPedidoDTORemetente.setStatus("CRIADO");
        dadosPedidoDTORemetente.setNome(remetenteDestinatarioEnderecoDTO.getRemetente().getNome());
        dadosPedidoDTORemetente.setTelefone(remetenteDestinatarioEnderecoDTO.getRemetente().getTelefone());
        dadosPedidoDTORemetente.setDataPedido(pedidoResponse.getDataPedido());
        enviarParaFilaService.enviarNotificacaoParaFila(dadosPedidoDTORemetente, enchangedNotificador);
        if (pedidoRequestDTO.getRemetenteId() != pedidoRequestDTO.getDestinatarioId()) {
            DadosPedidoDTO dadosPedidoDTODestinatario = new DadosPedidoDTO();
            dadosPedidoDTODestinatario.setPedidoId(pedidoResponse.getId());
            dadosPedidoDTODestinatario.setStatus("CRIADO");
            dadosPedidoDTODestinatario.setNome(remetenteDestinatarioEnderecoDTO.getDestinatario().getNome());
            dadosPedidoDTODestinatario.setTelefone(remetenteDestinatarioEnderecoDTO.getDestinatario().getTelefone());
            dadosPedidoDTODestinatario.setDataPedido(pedidoResponse.getDataPedido());
            enviarParaFilaService.enviarNotificacaoParaFila(dadosPedidoDTODestinatario, enchangedNotificador);
        }

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
                .busca(pedido.getRemetenteId(), pedido.getDestinatarioId(), pedido.getEnderecoId());

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
        try {
            UsuarioResponseEnderecoDTO usuario = cadastroResourceClient.buscarUsuarioPorId(id).getBody();
        } catch (FeignException.NotFound e) {
            throw new UsuarioNaoExistenteException("Usuário não encontrado");
        } catch (FeignException e) {
            throw new ServicoIndisponivelException("Serviço ms-gerenciador-cadastros indisponível");
        }
        List<Pedido> pedidos = pedidoRepository.findByRemetenteId(id);
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
        //Existe uma rota exclusiva para alterar o status do pedido para EM_ROTA
        if (pedido.getStatus().equalsIgnoreCase("EM_ROTA")) {
            throw new OperacaoInvalidaException("Status do pedido não pode ser alterado para EM_ROTA");
        }

        //Verificando a existencia do remetente, destinatário e endereço
        RemetenteDestinatarioEnderecoDTO remetenteDestinatarioEnderecoDTO = buscarRemetenteDestinatarioEnderecoService
                .busca(pedido.getRemetenteId(),
                        pedido.getDestinatarioId(),
                        pedido.getEnderecoId());
        //Verificando a existência do drone
        try {
            DroneDTO drone = cadastroResourceClient.buscarDronePorId(pedido.getDroneId()).getBody();
            if (pedidoExistente.getStatus().equals("CRIADO") && !Objects.equals(pedido.getDroneId(), pedidoExistente.getDroneId())
                    && !drone.getStatus().toUpperCase().equals("DISPONIVEL")) {
                throw new OperacaoInvalidaException("Não é possível alocar este drone para este pedido");
            }
        } catch (FeignException.NotFound e) {
            throw new DroneNaoExistenteException("Drone não encontrado");
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
        pedidoExistente.setRemetenteId(pedido.getRemetenteId());
        pedidoExistente.setDestinatarioId(pedido.getDestinatarioId());
        pedidoExistente.setEnderecoId(pedido.getEnderecoId());
        pedidoExistente.setStatus(StatusPedido.valueOf(pedido.getStatus().toUpperCase()));
        pedidoExistente.setDroneId(pedido.getDroneId());
        pedidoExistente.setDataEntrega(pedido.getDataEntrega());
        if (pedido.getDataEntrega() != null) {
            pedidoExistente.setDataEntrega(pedido.getDataEntrega());
        }
        Pedido pedidoEditado = pedidoRepository.save(pedidoExistente);

        //Enviando uma notificação para a fila
        DadosPedidoDTO dadosPedidoDTORemetente = new DadosPedidoDTO();
        dadosPedidoDTORemetente.setPedidoId(pedidoEditado.getId());
        dadosPedidoDTORemetente.setStatus(pedidoEditado.getStatus().toString());
        dadosPedidoDTORemetente.setNome(remetenteDestinatarioEnderecoDTO.getRemetente().getNome());
        dadosPedidoDTORemetente.setTelefone(remetenteDestinatarioEnderecoDTO.getRemetente().getTelefone());
        dadosPedidoDTORemetente.setDataPedido(pedidoEditado.getDataPedido());
        if (pedido.getDataEntrega() != null) {
            dadosPedidoDTORemetente.setDataEntrega(pedido.getDataEntrega());
        }
        enviarParaFilaService.enviarNotificacaoParaFila(dadosPedidoDTORemetente, enchangedNotificador);
        if (pedido.getRemetenteId() != pedido.getDestinatarioId()) {
            DadosPedidoDTO dadosPedidoDTODestinatario = new DadosPedidoDTO();
            dadosPedidoDTODestinatario.setPedidoId(pedidoEditado.getId());
            dadosPedidoDTODestinatario.setStatus(pedidoEditado.getStatus().toString());
            dadosPedidoDTODestinatario.setNome(remetenteDestinatarioEnderecoDTO.getDestinatario().getNome());
            dadosPedidoDTODestinatario.setTelefone(remetenteDestinatarioEnderecoDTO.getDestinatario().getTelefone());
            dadosPedidoDTODestinatario.setDataPedido(pedidoEditado.getDataPedido());
            if (pedido.getDataEntrega() != null) {
                dadosPedidoDTODestinatario.setDataEntrega(pedido.getDataEntrega());
            }
            enviarParaFilaService.enviarNotificacaoParaFila(dadosPedidoDTODestinatario, enchangedNotificador);
        }
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

    @Transactional
    public void colocarPedidoEM_ROTA(Long id, LatitudeLongitudeDTO latitudeLongitudeDTO) {
        Pedido pedido = pedidoRepository.findById(id).orElse(null);
        if (pedido == null) {
            throw new PedidoInexistenteException("Pedido não encontrado");
        }
        if (pedido.getStatus() != StatusPedido.CRIADO) {
            throw new OperacaoInvalidaException("Pedido não pode ser colocado em rota");
        }
        if (pedido.getDroneId() == null) {
            throw new OperacaoInvalidaException("Pedido não possui drone");
        }
        //Salvando as alterações no banco de dados
        pedido.setStatus(StatusPedido.EM_ROTA);
        pedidoRepository.save(pedido);

        // Criando um novo monitoramento
        MonitoramentoDTO monitoramentoDTO = new MonitoramentoDTO(pedido.getId(), pedido.getDroneId(),
                latitudeLongitudeDTO.getLatitude(), latitudeLongitudeDTO.getLongitude());
        //Enviando um novo monitoramento para a fila
        enviarParaFilaService.enviarNovoMonitoramentoParaFila(monitoramentoDTO, enchangedNovoMonitoramento);

        //Enviando uma notificação para a fila
        RemetenteDestinatarioEnderecoDTO remetenteDestinatarioEnderecoDTO = buscarRemetenteDestinatarioEnderecoService
                .busca(pedido.getRemetenteId(), pedido.getDestinatarioId(), pedido.getEnderecoId());

        DadosPedidoDTO dadosPedidoDTORemetente = new DadosPedidoDTO(pedido.getId(), "EM_ROTA",
                remetenteDestinatarioEnderecoDTO.getRemetente().getNome(),
                remetenteDestinatarioEnderecoDTO.getRemetente().getTelefone(),
                pedido.getDataPedido(), null);
        enviarParaFilaService.enviarNotificacaoParaFila(dadosPedidoDTORemetente, enchangedNotificador);

        if (pedido.getRemetenteId() != pedido.getDestinatarioId()) {
            DadosPedidoDTO dadosPedidoDTODestinatario = new DadosPedidoDTO(pedido.getId(), "EM_ROTA",
                    remetenteDestinatarioEnderecoDTO.getDestinatario().getNome(),
                    remetenteDestinatarioEnderecoDTO.getDestinatario().getTelefone(),
                    pedido.getDataPedido(), null);
            enviarParaFilaService.enviarNotificacaoParaFila(dadosPedidoDTODestinatario, enchangedNotificador);
        }
    }
}
