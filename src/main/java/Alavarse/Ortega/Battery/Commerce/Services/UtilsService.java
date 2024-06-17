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

@Service
public class UtilsService {
    private static final String URI = "https://sandbox.tecno.mobi/api/v1/";
    private static final String AGILE_EMAIL = "macdavismotos418@gmail.com";
    private static final String AGILE_PASSWORD = "felipe88535581";
    private static final String AGILE_EMPRESA_IDPK = "5397";

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

        System.out.println("json" + json);

        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(java.net.URI.create(URI + "Login/Logar"))
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
}
