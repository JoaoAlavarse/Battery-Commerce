package Alavarse.Ortega.Battery.Commerce.Services;

import Alavarse.Ortega.Battery.Commerce.DTOs.Payment.PaymentCardRequestDTO;
import Alavarse.Ortega.Battery.Commerce.DTOs.Payment.PaymentPixRequestDTO;
import Alavarse.Ortega.Battery.Commerce.DTOs.Sale.SaleDTO;
import Alavarse.Ortega.Battery.Commerce.Entities.CardEntity;
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
    @Autowired
    private CardService cardService;

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
                PaymentEntity payment = new PaymentEntity(fmp_idpk, "Pix", PaymentStatus.PENDENTE);
                this.repository.save(payment);
                SaleEntity sale = this.saleService.create(pixData.saleData(), payment);
                System.out.println("Response" + response.body());
                return response.body();
            }
            throw new RuntimeException();
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public String createCard(PaymentCardRequestDTO cardData){
        UtilsEntity utils = this.utilsService.getByKey("tokenAgile");
        HttpClient client = HttpClient.newHttpClient();

        String json = """
                {
                    "fmc_descricao": "%s",
                    "fmc_cliente_nome": "%s",
                    "fmc_cliente_documento": "%s",
                    "fmc_qtde_parcelas": "1",
                    "fmc_valor": "%s"
                }
                """.formatted(cardData.fmc_description(), cardData.fmc_user_name(), cardData.fmc_user_document(), cardData.value());

        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + utils.getValue())
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(URI.create(AGILE_URI + "Cartao/Inserir?empresa_idpk=" + AGILE_EMPRESA_IDPK))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response.body());

                JsonNode registrosArray = jsonNode.get("registros");

                JsonNode firstRegistro = registrosArray.get(0);

                String fmc_idpk = firstRegistro.get("fmc_idpk").asText();


                PaymentEntity payment = new PaymentEntity(fmc_idpk, "Cartao", PaymentStatus.PENDENTE);
                this.repository.save(payment);
                SaleEntity sale = this.saleService.create(cardData.saleData(), payment);

                this.repository.save(payment);
                payCard(fmc_idpk, cardData.cardId());
            return response.body();
            }
           throw new RuntimeException();
        } catch (Exception e){
            throw new RuntimeException();
        }
    }

    public void payCard(String fmc_idpk, String cardId){
        UtilsEntity utils = this.utilsService.getByKey("tokenAgile");
        HttpClient client = HttpClient.newHttpClient();
        CardEntity card = this.cardService.getById(cardId);

        String json = """
                {
                    "pagador": {
                        "nome": "%s",
                        "documento": "%s"
                    },
                    "cartao": {
                        "titular_nome": "%s",
                        "titular_documento": "%s",
                        "numero": "%s",
                        "cvc": "%s",
                        "validade_mes": "07",
                        "validade_ano": "2026"
                    }
                }
                """.formatted(card.getUser().getName(), card.getUser().getDocument(), card.getCardOwner() ,card.getOwnerDocument(),
                card.getCardNumber(), card.getCvv());

        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + utils.getValue())
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .uri(URI.create(AGILE_URI + "Cartao/Pagar/" + fmc_idpk + "?empresa_idpk=" + AGILE_EMPRESA_IDPK))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RuntimeException();
            }
        } catch (Exception e){
            throw new RuntimeException();
        }
    }
}
