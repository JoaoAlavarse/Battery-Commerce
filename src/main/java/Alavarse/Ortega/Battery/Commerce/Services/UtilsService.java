package Alavarse.Ortega.Battery.Commerce.Services;

import Alavarse.Ortega.Battery.Commerce.Entities.UtilsEntity;
import Alavarse.Ortega.Battery.Commerce.Repositories.UtilsRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static Alavarse.Ortega.Battery.Commerce.Constants.AgilePayConstants.*;

@Service
public class UtilsService {

    @Autowired
    private UtilsRepository repository;

    public void loginAgilePay(){
        HttpClient client = HttpClient.newHttpClient();

        String json = """
                {
                    "email": "%s",
                    "senha": "%s"
                }
                """.formatted(AGILE_EMAIL, AGILE_PASSWORD);

        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(java.net.URI.create(AGILE_URI + "Login/Logar"))
                .build();

        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.body());

            String token = jsonNode.get("token").asText();

            System.out.println(token);

            UtilsEntity utils = repository.findById(1).orElseThrow(RuntimeException::new);
            utils.setValue(token);
            repository.save(utils);

        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("alhjkbfoulashdbf");
        }
    }

    public UtilsEntity getByKey(String key){
        return this.repository.getByKey(key).orElseThrow(RuntimeException::new);
    }
}
