package com.example.ms_gerenciador_.cadastros.listener;

import com.example.ms_gerenciador_.cadastros.model.Endereco;
import com.example.ms_gerenciador_.cadastros.service.EnviarParaFilaService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeadLetterEnderecoPendenteLatLongListener {

    @Autowired
    private EnviarParaFilaService latitudeLongitudeService;

    @RabbitListener(queues = "endereco-pendente.ms-gereciadorcadastro-DLQ")
    public void deadLetterListener(Endereco enderecoRecebidoDaFila) throws InterruptedException {
        if (enderecoRecebidoDaFila.getTentativas() > 5) {
            System.out.println("Endereço não foi processado");
        } else {
            Thread.sleep(300000);
            latitudeLongitudeService.enviarEnderecoParaFila(enderecoRecebidoDaFila, "endereco-pendente.ex");
        }
    }
}
