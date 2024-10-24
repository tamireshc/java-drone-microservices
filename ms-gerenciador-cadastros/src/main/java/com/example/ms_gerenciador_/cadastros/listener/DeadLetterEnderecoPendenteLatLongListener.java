package com.example.ms_gerenciador_.cadastros.listener;

import com.example.ms_gerenciador_.cadastros.model.Endereco;
import com.example.ms_gerenciador_.cadastros.service.EnviarParaFilaService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class DeadLetterEnderecoPendenteLatLongListener {

    @Autowired
    private EnviarParaFilaService latitudeLongitudeService;
    @Value("${rabbitmq.enderecopendente.exchange}")
    private String enchangedEnderecoPendente;
    private static final String queueEnderecoPendenteDLQ = "${rabbitmq.enderecopendente.queueDLQ}";
    private static final Logger logger = Logger.getLogger(DeadLetterEnderecoPendenteLatLongListener.class.getName());
    private static final String msgErro = "Endereço não foi processado, não foi possivel obter os valores de latitude e longitude da API";

    @RabbitListener(queues = queueEnderecoPendenteDLQ)
    public void deadLetterListener(Endereco enderecoRecebidoDaFila) throws InterruptedException {
        if (enderecoRecebidoDaFila.getTentativas() > 5) {
            System.out.println(msgErro);
            logger.log(Level.SEVERE, msgErro);
        } else {
            Thread.sleep(10000);
            latitudeLongitudeService.enviarEnderecoParaFila(enderecoRecebidoDaFila, enchangedEnderecoPendente);
        }
    }
}
