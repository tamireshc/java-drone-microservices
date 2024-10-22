package com.example.ms_gerenciador_.cadastros.listener;

import com.example.ms_gerenciador_.cadastros.dto.LatitudeLongitudeDTO;
import com.example.ms_gerenciador_.cadastros.model.Endereco;
import com.example.ms_gerenciador_.cadastros.service.EnderecoService;
import com.example.ms_gerenciador_.cadastros.utils.FetchLatitudeLongitudeApi;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LatitudeLongitudeListener {

    Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
    private final String apiKey = dotenv.get("API_KEY");

    @Autowired
    EnderecoService enderecoService;


    @RabbitListener(queues = "endereco-pendente.ms-gereciadorcadastro")
    @Transactional
    public void completarEnderecoComLatitudeLongitude(Endereco endereco) {
        LatitudeLongitudeDTO latitudeLongitudeDTO = FetchLatitudeLongitudeApi.fetchLatitudeLongitude(endereco, apiKey);
        endereco.setLatitude(latitudeLongitudeDTO.getLatitude());
        endereco.setLongitude(latitudeLongitudeDTO.getLongitude());
        Endereco enderecoAtualizado = enderecoService.editarEndereco(endereco.getId(), endereco);
    }
}
