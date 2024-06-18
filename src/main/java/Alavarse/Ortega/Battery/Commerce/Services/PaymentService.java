package Alavarse.Ortega.Battery.Commerce.Services;

import Alavarse.Ortega.Battery.Commerce.DTOs.Payment.PaymentCardRequestDTO;
import Alavarse.Ortega.Battery.Commerce.DTOs.Payment.PaymentPixRequestDTO;
import Alavarse.Ortega.Battery.Commerce.Entities.*;
import Alavarse.Ortega.Battery.Commerce.Enums.DeliveryStatus;
import Alavarse.Ortega.Battery.Commerce.Enums.PaymentStatus;
import Alavarse.Ortega.Battery.Commerce.Exceptions.PaymentExceptions.Card.UnableToCreateCardPaymentException;
import Alavarse.Ortega.Battery.Commerce.Exceptions.PaymentExceptions.Card.UnableToMakeCardPaymentException;
import Alavarse.Ortega.Battery.Commerce.Exceptions.PaymentExceptions.Pix.UnableToCreatePixPaymentException;
import Alavarse.Ortega.Battery.Commerce.Repositories.PaymentRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<String> createPix(PaymentPixRequestDTO pixData){
        UtilsEntity utils = this.utilsService.getByKey("tokenAgile");
        HttpClient client = HttpClient.newHttpClient();

        String json = """
                {
                    "fmp_descricao": "%s",
                    "fmp_valor": %s
                }
                """.formatted(pixData.fmp_description(), pixData.fmp_value());

        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + utils.getValue())
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(URI.create(AGILE_URI + "Pix/Instantaneo?empresa_idpk=" + AGILE_EMPRESA_IDPK))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response.body());

                JsonNode registrosArray = jsonNode.get("registros");

                JsonNode firstRegistro = registrosArray.get(0);

                String fmp_idpk = firstRegistro.get("fmp_idpk").asText();

                PaymentEntity payment = new PaymentEntity(fmp_idpk, "Pix", PaymentStatus.PENDENTE);
                this.repository.save(payment);
                this.saleService.create(pixData.saleData(), payment);
                return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response.body());
            }
            throw new UnableToCreatePixPaymentException();
        } catch (Exception e){
            throw new UnableToCreatePixPaymentException();
        }
    }

    public ResponseEntity<String> createCard(PaymentCardRequestDTO cardData){
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
            if (response.statusCode() != 200) {
                throw new UnableToCreateCardPaymentException();
            }
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.body());

            JsonNode registrosArray = jsonNode.get("registros");

            JsonNode firstRegistro = registrosArray.get(0);

            String fmc_idpk = firstRegistro.get("fmc_idpk").asText();


            PaymentEntity payment = new PaymentEntity(fmc_idpk, "Cartao", PaymentStatus.PENDENTE);
            this.repository.save(payment);
            this.saleService.create(cardData.saleData(), payment);
            payCard(fmc_idpk, cardData.cardId());
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response.body());
        } catch (Exception e){
            throw new UnableToCreateCardPaymentException();
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
                throw new UnableToMakeCardPaymentException();
            }

            updateDeliveryStatus(fmc_idpk);


        } catch (Exception e){
            throw new UnableToMakeCardPaymentException();
        }
    }

    public PaymentEntity getById(String idpk){
        return this.repository.findById(idpk).orElseThrow(RuntimeException::new);
    }

    public void updateDeliveryStatus(String idpk){
        PaymentEntity payment = this.getById(idpk);
        DeliveryEntity delivery = payment.getSale().getDelivery();
        deliveryService.updateStatus(delivery.getDeliveryId(), DeliveryStatus.CONFIRMADO);
    }
}
