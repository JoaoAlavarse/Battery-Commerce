package Alavarse.Ortega.Battery.Commerce.Services;

import Alavarse.Ortega.Battery.Commerce.DTOs.Freight.FreightResponseDTO;
import Alavarse.Ortega.Battery.Commerce.Exceptions.FreightExceptions.ErrorWhileGettingFreightException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
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

    public FreightResponseDTO getFreightInfo(String cep, int quantity) {
        float totalWeight = DEFAULT_WEIGHT * quantity;
        List<Float> weights = calculateWeights(totalWeight);

        float totalFreightCost = 0.0f;
        int estimateDays = 0;
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

                estimateDays = parseEstimativeDays(response.body());

                float freightCost = parseFreightCost(response.body());
                totalFreightCost += freightCost;

            } catch (Exception e) {
                throw new ErrorWhileGettingFreightException();
            }
        }

        return new FreightResponseDTO(BigDecimal.valueOf(totalFreightCost), estimateDays);
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
        try {
            org.json.JSONArray jsonArray = new org.json.JSONArray(responseBody);
            org.json.JSONObject jsonObject;

            if (jsonArray.length() > 1) {
                jsonObject = jsonArray.getJSONObject(1);
            } else if (jsonArray.length() > 0) {
                jsonObject = jsonArray.getJSONObject(0);
            } else {
                throw new ErrorWhileGettingFreightException();
            }

            return jsonObject.getFloat("defaultValue");
        } catch (Exception e) {
            throw new ErrorWhileGettingFreightException();
        }
    }

    private int parseEstimativeDays(String responseBody) {
        try {
            org.json.JSONArray jsonArray = new org.json.JSONArray(responseBody);
            org.json.JSONObject jsonObject;

            if (jsonArray.length() > 1) {
                jsonObject = jsonArray.getJSONObject(1);
            } else if (jsonArray.length() > 0) {
                jsonObject = jsonArray.getJSONObject(0);
            } else {
                throw new ErrorWhileGettingFreightException();
            }

            return jsonObject.getInt("estimateDays");
        } catch (Exception e) {
            throw new ErrorWhileGettingFreightException();
        }
    }
}
