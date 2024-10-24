package com.example.ms_gerenciador_.cadastros.listener;

import com.example.ms_gerenciador_.cadastros.dto.LatitudeLongitudeDTO;
import com.example.ms_gerenciador_.cadastros.model.Endereco;
import com.example.ms_gerenciador_.cadastros.service.EnderecoService;
import com.example.ms_gerenciador_.cadastros.service.EnviarParaFilaService;
import com.example.ms_gerenciador_.cadastros.utils.FetchLatitudeLongitudeApi;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnderecoPendenteLatLonListener {

    Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
    private final String apiKey = dotenv.get("API_KEY");

    @Autowired
    EnderecoService enderecoService;
    @Autowired
    EnviarParaFilaService latitudeLongitudeService;

    @RabbitListener(queues = "endereco-pendente.ms-gereciadorcadastro")
    @Transactional
    public void completarEnderecoComLatitudeLongitude(Endereco endereco) {
        FetchLatitudeLongitudeApi fetchLatitudeLongitudeApi = new FetchLatitudeLongitudeApi(latitudeLongitudeService);
        LatitudeLongitudeDTO latitudeLongitudeDTO = fetchLatitudeLongitudeApi.fetchLatitudeLongitude(endereco, apiKey);
        try {
            endereco.setLatitude(latitudeLongitudeDTO.getLatitude());
            endereco.setLongitude(latitudeLongitudeDTO.getLongitude());

            Endereco enderecoAtualizado = enderecoService.editarEndereco(endereco.getId(), endereco);
        } catch (Exception e) {
            latitudeLongitudeService.enviarEnderecoParaFila(endereco, "endereco-pendente-DLQ.ex");
        }
    }
}
