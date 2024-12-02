package com.example.ms_notificador.service;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoSNSService {
    @Autowired
    private AmazonSNS amazonSNS;

    public void notificar(String telefone, String message) {
        PublishRequest publishRequest = new PublishRequest().withMessage(message)
                .withPhoneNumber(telefone);
        amazonSNS.publish(publishRequest);
    }
}
