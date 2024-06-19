package Alavarse.Ortega.Battery.Commerce.Services;

import Alavarse.Ortega.Battery.Commerce.DTOs.Payment.Card.PaymentCardRequestDTO;
import Alavarse.Ortega.Battery.Commerce.DTOs.Payment.Pix.PaymentPixRequestDTO;
import Alavarse.Ortega.Battery.Commerce.DTOs.Payment.Ticket.PaymentTicketRequestDTO;
import Alavarse.Ortega.Battery.Commerce.Entities.*;
import Alavarse.Ortega.Battery.Commerce.Enums.DeliveryStatus;
import Alavarse.Ortega.Battery.Commerce.Enums.PaymentStatus;
import Alavarse.Ortega.Battery.Commerce.Exceptions.PaymentExceptions.Card.UnableToCreateCardPaymentException;
import Alavarse.Ortega.Battery.Commerce.Exceptions.PaymentExceptions.Card.UnableToMakeCardPaymentException;
import Alavarse.Ortega.Battery.Commerce.Exceptions.PaymentExceptions.ErrorWhileGettingPaymentException;
import Alavarse.Ortega.Battery.Commerce.Exceptions.PaymentExceptions.Pix.UnableToCreatePixPaymentException;
import Alavarse.Ortega.Battery.Commerce.Exceptions.PaymentExceptions.Ticket.UnableToCreateTicketPaymentException;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    @Autowired
    private UserService userService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private CartService cartService;


    public ResponseEntity<String> createPix(PaymentPixRequestDTO pixData){
        UtilsEntity utils = this.utilsService.getByKey("tokenAgile");
        HttpClient client = HttpClient.newHttpClient();
        CartEntity cart = this.cartService.getById(pixData.saleData().cartId());

        String json = """
                {
                    "fmp_descricao": "%s",
                    "fmp_valor": %s
                }
                """.formatted(pixData.fmp_description(), cart.getTotalValue().add(pixData.saleData().freightValue()));

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
                String fmp_link_qrcode = firstRegistro.get("fmp_link_qrcode").asText();

                PaymentEntity payment = new PaymentEntity(fmp_idpk, fmp_link_qrcode, "Pix", PaymentStatus.PENDENTE);
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
        CartEntity cart = this.cartService.getById(cardData.saleData().cartId());

        String json = """
                {
                    "fmc_descricao": "%s",
                    "fmc_cliente_nome": "%s",
                    "fmc_cliente_documento": "%s",
                    "fmc_qtde_parcelas": "1",
                    "fmc_valor": "%s"
                }
                """.formatted(cardData.fmc_description(), cart.getUser().getName(), cart.getUser().getDocument(), cart.getTotalValue().add(cardData.saleData().freightValue()));

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


            PaymentEntity payment = new PaymentEntity(fmc_idpk, null, "Cartao", PaymentStatus.PENDENTE);
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

            PaymentEntity payment = this.repository.findById(fmc_idpk).orElseThrow(ErrorWhileGettingPaymentException::new);
            payment.setStatus(PaymentStatus.PAGO);
            this.repository.save(payment);
            updateDeliveryStatus(fmc_idpk);


        } catch (Exception e){
            throw new UnableToMakeCardPaymentException();
        }
    }

    public void updateDeliveryStatus(String idpk){
        DeliveryEntity delivery = this.deliveryService.getByPaymentId(idpk);
        deliveryService.updateStatus(delivery.getDeliveryId(), DeliveryStatus.CONFIRMADO);
    }

    public ResponseEntity<String> createTicket(PaymentTicketRequestDTO ticketData){
        UtilsEntity utils = this.utilsService.getByKey("tokenAgile");
        HttpClient client = HttpClient.newHttpClient();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.now().plusDays(3);
        String convertedDate = date.format(formatter);

        UserEntity user = this.userService.findById(ticketData.saleData().userId());
        AddressEntity address = this.addressService.getById(ticketData.saleData().addressId());
        CartEntity cart = this.cartService.getById(ticketData.saleData().cartId());

        String json = """
                {
                    "fmb_sacado_nome": "%s",
                    "fmb_sacado_cnpj_cpf": "%s",
                    "fmb_sacado_endereco": "%s",
                    "fmb_sacado_endereco_numero": "%s",
                    "fmb_sacado_bairro": "%s",
                    "fmb_sacado_cep": "%s",
                    "fmb_sacado_cidade": "%s",
                    "fmb_sacado_uf": "%s",
                    "fmb_valor": "%s",
                    "fmb_vencimento": "%S"
                }
                """.formatted(user.getName(), user.getDocument(), address.getAddress(), address.getNumber(), address.getNeighborhood(),
                        address.getCEP(), address.getCity(), address.getState(), cart.getTotalValue().add(ticketData.saleData().freightValue()), convertedDate);

        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + utils.getValue())
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(URI.create(AGILE_URI + "Boleto/Inserir?empresa_idpk=" + AGILE_EMPRESA_IDPK))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new UnableToCreateTicketPaymentException();
            }
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.body());
            JsonNode registrosArray = jsonNode.get("registros");
            JsonNode firstRegistro = registrosArray.get(0);

            String fmb_idpk = firstRegistro.get("fmb_idpk").asText();
            String fmb_link_url = firstRegistro.get("fmb_link_url").asText();

            PaymentEntity payment = new PaymentEntity(fmb_idpk, fmb_link_url ,"Boleto", PaymentStatus.PENDENTE);
            this.repository.save(payment);
            this.saleService.create(ticketData.saleData(), payment);

            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response.body());

        } catch (Exception e){
            throw new UnableToCreateTicketPaymentException();
        }
    }

}
