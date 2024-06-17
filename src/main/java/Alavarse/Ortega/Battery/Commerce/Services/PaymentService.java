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
        UtilsEntity utils = this.utilsService.getByKey("tokenAgile");
        HttpClient client = HttpClient.newHttpClient();

        System.out.println(utils.getValue());

        String json = """
                {
                    "fmp_descricao": "%s",
                    "fmp_valor": %s
                }
                """.formatted(pixData.fmp_description(), pixData.fmp_value());

        System.out.println(json);

        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MTg2NjQ1NDgsImV4cCI6MTcxODc0Mzc0OCwiaXNzIjoiQWxmYSBTaXN0ZW1hcyIsImFpcyI6Ik1lcmN1cmlvIEFQSSIsInVzdV9pZHBrIjoiNTQzIiwidXN1X25vbWUiOiJtYWNkYXZpcyIsInRpcG9fYWNlc3NvIjoiVXN1XHUwMEUxcmlvIiwiZW1haWwiOiJtYWNkYXZpc21vdG9zNDE4QGdtYWlsLmNvbSIsInNpc3RlbWFfaWRwayI6NjQsImVtcHJlc2FzIjpbNTM5N119.p4aKlRfEp9OP0jZ57GfigNn0le-8TU8O4h8sgfMyMG0")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(URI.create(AGILE_URI + "Pix/Instantaneo?empresa_idpk=" + AGILE_EMPRESA_IDPK))
                .build();
        System.out.println("teste " + AGILE_URI + "Pix/Instantaneo?empresa_idpk=" + AGILE_EMPRESA_IDPK);


        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("response" + response.body() + response.statusCode());
            if (response.statusCode() == 200) {

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response.body());

                JsonNode registrosArray = jsonNode.get("registros");

                System.out.println("node " + jsonNode);
                System.out.println("array " + registrosArray);


                JsonNode firstRegistro = registrosArray.get(0);

                System.out.println("1 " + firstRegistro);
                String fmp_idpk = firstRegistro.get("fmp_idpk").asText();

                System.out.println("fmp " + fmp_idpk);

                SaleEntity sale = this.saleService.create(pixData.saleData());
                PaymentEntity payment = new PaymentEntity(fmp_idpk, "Pix", PaymentStatus.PENDENTE, sale);
                this.repository.save(payment);
                System.out.println("Response" + response.body());
                return response.body();
            }
            throw new RuntimeException();
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
