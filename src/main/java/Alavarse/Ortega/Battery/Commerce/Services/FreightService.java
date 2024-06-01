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
import java.util.ArrayList;
import java.util.List;

@Service
public class FreightService {
    private static final String URI = "https://api.site.pagseguro.uol.com.br/ps-website-bff/v1/shipment/simulate";
    private static final float DEFAULT_WEIGHT = 2.4f;
    private static final float MAX_WEIGHT = 10.0f;

    public ResponseEntity<String> getFreightInfo(String cep, int quantity) {
        float totalWeight = DEFAULT_WEIGHT * quantity;
        List<Float> weights = calculateWeights(totalWeight);

        List<String> responses = new ArrayList<>();
        float totalFreightCost = 0.0f;
        HttpClient client = HttpClient.newHttpClient();

        for (float weight : weights) {
            String json = """
            {
                "postalSender": "87200000",
                "postalReceiver": "%s",
                "length": "15",
                "height": "15",
                "width": "15",
                "weight": "%s",
                "productValue": 200
            }
            """.formatted(cep, String.valueOf(weight));

            HttpRequest request = HttpRequest.newBuilder()
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .uri(java.net.URI.create(URI)).build();

            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(response.body());
                responses.add(response.body());

                // Parse the response to extract the defaultValue (default value)
                float freightCost = parseFreightCost(response.body());
                totalFreightCost += freightCost;

            } catch (Exception e) {
                throw new ErrorWhileGettingFreightException();
            }
        }

        // Create a JSON response with the total freight cost and all responses
        String totalFreightCostJson = """
        {
            "totalFreightCost": %s,
            "responses": %s
        }
        """.formatted(String.valueOf(totalFreightCost), responses);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(totalFreightCostJson);
    }

    private List<Float> calculateWeights(float totalWeight) {
        List<Float> weights = new ArrayList<>();
        while (totalWeight > MAX_WEIGHT) {
            weights.add(MAX_WEIGHT);
            totalWeight -= MAX_WEIGHT;
        }
        if (totalWeight > 0) {
            weights.add(totalWeight);
        }
        return weights;
    }

    private float parseFreightCost(String responseBody) {
        // Assuming the response is a JSON array as provided in the example
        // [{"provider":"Correios","contractValue":19.45,...}]
        try {
            org.json.JSONArray jsonArray = new org.json.JSONArray(responseBody);
            org.json.JSONObject firstObject = jsonArray.getJSONObject(1); // Assuming SEDEX is always at index 1
            return firstObject.getFloat("defaultValue");
        } catch (Exception e) {
            throw new ErrorWhileGettingFreightException();
        }
    }
}
