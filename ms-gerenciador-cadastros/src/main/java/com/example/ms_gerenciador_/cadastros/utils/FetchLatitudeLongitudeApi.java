package com.example.ms_gerenciador_.cadastros.utils;

import com.example.ms_gerenciador_.cadastros.dto.LatitudeLongitudeDTO;
import com.example.ms_gerenciador_.cadastros.model.Endereco;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class FetchLatitudeLongitudeApi {

    public static String URL ="https://api-v2.distancematrix.ai/maps/api/geocode/json?address=";

    public static LatitudeLongitudeDTO fetchLatitudeLongitude(Endereco endereco, String apiKey) {

        String enderecoCodificado = URLEncoder.encode(
                endereco.getNumero() + " "
                        + endereco.getLogradouro() + " "
                        + endereco.getCidade() + " "
                        + endereco.getEstado(),
                StandardCharsets.UTF_8
        );

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL
                            +enderecoCodificado
                            +"+&key="
                            +apiKey))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            LatitudeLongitudeDTO latitudeLongitudeDTO = new LatitudeLongitudeDTO();
            latitudeLongitudeDTO.setLatitude(response.body().split("lat\":")[1].split(",")[0]);
            latitudeLongitudeDTO.setLongitude(response.body().split("lng\":")[1].split("}")[0]);
            return latitudeLongitudeDTO;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
