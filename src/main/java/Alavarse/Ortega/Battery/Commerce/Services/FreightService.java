package Alavarse.Ortega.Battery.Commerce.Services;

import Alavarse.Ortega.Battery.Commerce.DTOs.FreightRequestDTO;
import Alavarse.Ortega.Battery.Commerce.Exceptions.Freight.ErrorWhileGettingFreightException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class FreightService {
    private static final String URI = "https://api.site.pagseguro.uol.com.br/ps-website-bff/v1/shipment/simulate";
    private static final float DEFAULT_WEIGHT = 2.4f;

    public ResponseEntity<String> getFreightInfo(String cep, int quantity){
        float weight = DEFAULT_WEIGHT * quantity;
        String json = """
        {
            "postalSender": "87200000",
            "postalReceiver": "%s",
            "length": "30",
            "height": "30",
            "width": "30",
            "weight": "%s",
            "productValue": 200
        }
        """.formatted(cep, weight);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(java.net.URI.create(URI)).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response.body());
        } catch (Exception e) {
            throw new ErrorWhileGettingFreightException();
        }
    }
}
