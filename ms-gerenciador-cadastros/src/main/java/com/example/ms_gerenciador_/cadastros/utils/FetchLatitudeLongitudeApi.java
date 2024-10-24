package com.example.ms_gerenciador_.cadastros.utils;

import com.example.ms_gerenciador_.cadastros.dto.LatitudeLongitudeDTO;
import com.example.ms_gerenciador_.cadastros.model.Endereco;
import com.example.ms_gerenciador_.cadastros.service.EnviarParaFilaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class FetchLatitudeLongitudeApi {

    private final EnviarParaFilaService latitudeLongitudeService;
    @Value("${url.api.distancematrix}")
    private String urlApiDistancematrix;

    @Autowired
    public FetchLatitudeLongitudeApi(EnviarParaFilaService latitudeLongitudeService) {
        this.latitudeLongitudeService = latitudeLongitudeService;
    }

    private static final Logger logger = Logger.getLogger(FetchLatitudeLongitudeApi.class.getName());
    private static final String msgErro = "Erro ao realizar fetch latitude e longitude";
    public static String URL = "https://api-v2.distancematrix.ai/maps/api/geocode/json?address=";

    public LatitudeLongitudeDTO fetchLatitudeLongitude(Endereco endereco, String apiKey) {

        String enderecoCodificado = URLEncoder.encode(
                endereco.getNumero() + " "
                        + endereco.getLogradouro() + " "
                        + endereco.getCidade() + " "
                        + endereco.getEstado(),
                StandardCharsets.UTF_8);
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL
                            + enderecoCodificado
                            + "+&key="
                            + apiKey))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            LatitudeLongitudeDTO latitudeLongitudeDTO = new LatitudeLongitudeDTO();
            latitudeLongitudeDTO.setLatitude(response.body().split("lat\":")[1].split(",")[0]);
            latitudeLongitudeDTO.setLongitude(response.body().split("lng\":")[1].split("}")[0]);
            return latitudeLongitudeDTO;

        } catch (Exception e) {
            System.out.println(msgErro);
            logger.log(Level.SEVERE, msgErro);
        }
        return null;
    }
}
