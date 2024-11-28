package com.example.ms_monitoramento.listener;

import com.example.ms_monitoramento.dto.MonitoramentoDTO;
import com.example.ms_monitoramento.model.Monitoramento;
import com.example.ms_monitoramento.service.MonitoramentoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NovoMonitoramentoListener {

    private static final String queueNovoMonitoramento = "${rabbitmq.novomonitoramento.queue}";
    @Value("${rabbitmq.novomonitoramento.exchange}")
    private String enchangedNovoMonitoramento;
    @Autowired
    private MonitoramentoService monitoramentoService;

    @RabbitListener(queues = queueNovoMonitoramento)
    @Transactional
    public void criarNovoMonitoramento(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            MonitoramentoDTO monitoramentoDTO = objectMapper.readValue(message, MonitoramentoDTO.class);
            Monitoramento monitoramento = new Monitoramento();
            monitoramento.setPedidoId(monitoramentoDTO.getPedidoId());
            monitoramento.setDroneId(monitoramentoDTO.getDroneId());
            monitoramento.setLatitude(monitoramentoDTO.getLatitude());
            monitoramento.setLongitude(monitoramentoDTO.getLongitude());
            monitoramentoService.criarMonitoramento(monitoramento);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
