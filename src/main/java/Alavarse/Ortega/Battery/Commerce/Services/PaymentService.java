package Alavarse.Ortega.Battery.Commerce.Services;

import Alavarse.Ortega.Battery.Commerce.DTOs.Payment.PaymentPixRequestDTO;
import Alavarse.Ortega.Battery.Commerce.DTOs.Sale.SaleDTO;
import Alavarse.Ortega.Battery.Commerce.Entities.PaymentEntity;
import Alavarse.Ortega.Battery.Commerce.Entities.SaleEntity;
import Alavarse.Ortega.Battery.Commerce.Entities.UtilsEntity;
import Alavarse.Ortega.Battery.Commerce.Enums.PaymentStatus;
import Alavarse.Ortega.Battery.Commerce.Repositories.PaymentRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static Alavarse.Ortega.Battery.Commerce.Constants.AgilePayConstants.AGILE_EMPRESA_IDPK;
import static Alavarse.Ortega.Battery.Commerce.Constants.AgilePayConstants.AGILE_URI;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository repository;
    @Autowired
    private SaleService saleService;
    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private UtilsService utilsService;

    public String createPix(PaymentPixRequestDTO pixData){
        UtilsEntity utils = this.utilsService.getByKey("agileToken");
        HttpClient client = HttpClient.newHttpClient();

        String json = """
                {
                    "fmp_descricao": "%s",
                    "fmp_valor": %s,
                    "fmp_taxa": 3.25
                }
                """.formatted(pixData.fmp_description(), pixData.fmp_value());

        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .header("Authorization", utils.getValue())
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(URI.create(AGILE_URI + "Pix/Instantaneo?empresa_idpk=" + AGILE_EMPRESA_IDPK))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.body());

            String fmp_idpk = jsonNode.get("fmp_idpk").asText();

            SaleEntity sale = this.saleService.create(pixData.saleData());
            PaymentEntity payment = new PaymentEntity(fmp_idpk, "Pix", PaymentStatus.PENDENTE, sale);
            this.repository.save(payment);
            System.out.println("Response" + response.body());
            return response.body();
        } catch (Exception e){
            throw new RuntimeException();
        }
    }
}
