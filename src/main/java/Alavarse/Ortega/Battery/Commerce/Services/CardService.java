package Alavarse.Ortega.Battery.Commerce.Services;

import Alavarse.Ortega.Battery.Commerce.DTOs.CardDTO;
import Alavarse.Ortega.Battery.Commerce.DTOs.CardResponseDTO;
import Alavarse.Ortega.Battery.Commerce.DTOs.UpdateCardDTO;
import Alavarse.Ortega.Battery.Commerce.Entities.CardEntity;
import Alavarse.Ortega.Battery.Commerce.Repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CardService {
    @Autowired
    private CardRepository repository;
    @Autowired
    private EncryptService encryptService;
    @Autowired
    private UserService userService;

    public CardResponseDTO create(CardDTO data){
        verifyCardQuantity(data.userId());
        validateCardNumber(data.cardNumber());
        String flag = getCardType(data.cardNumber());
        validateDate(data.expirationDate());
        try{
            CardEntity card = new CardEntity(encryptService.encrypt(data.cardNumber()), encryptService.encrypt(data.cardOwner()),
                    encryptService.encrypt(data.expirationDate()), encryptService.encrypt(data.cvv()), encryptService.encrypt(flag),
                    userService.findById(data.userId()), data.main());
            CardEntity newCard = repository.save(card);
            return new CardResponseDTO(makeCardNumberResponse(newCard.getCardNumber()));
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    private String makeCardNumberResponse(String encryptedCardNumber){
        String decryptedCardNumber = encryptService.decrypt(encryptedCardNumber);
        return decryptedCardNumber.substring(decryptedCardNumber.length() - 4);
    }

    public CardEntity setCardMainTrue(String id){
        CardEntity card = this.repository.findById(id).orElseThrow(RuntimeException::new);
        setCardMainFalse(id);
        card.setMain(true);
        return repository.save(card);
    }

    private void setCardMainFalse(String id){
        List<CardEntity> list = this.repository.findByUser(this.userService.findById(id));
        list.forEach(cardEntity -> {
            cardEntity.setMain(false);
            this.repository.save(cardEntity);
        });
    }

    private void verifyCardQuantity(String userId){
        if (this.repository.findByUser(this.userService.findById(userId)).size() >= 3){
            throw new RuntimeException();
        }
    }

    public List<CardResponseDTO> getAllByUser(String userId){
        List<CardResponseDTO> list = new ArrayList<>();
        this.repository.findByUser(this.userService.findById(userId)).forEach(cardEntity -> {
            list.add(new CardResponseDTO(this.makeCardNumberResponse(cardEntity.getCardNumber())));
        });
        return list;
    }

    public void updateCard(UpdateCardDTO data, String cardId){
        CardEntity card = this.repository.findById(cardId).orElseThrow(RuntimeException::new);
        this.validateDate(data.expirationDate());
        try {
            card.setCardOwner(this.encryptService.encrypt(data.cardOwner()));
            card.setExpirationDate(this.encryptService.encrypt(data.expirationDate()));
            repository.save(card);
        } catch (Exception e){
            throw new RuntimeException();
        }
    }

    public void delete(String cardId){
        try{
            this.repository.deleteById(cardId);
        } catch (Exception e){
            throw new RuntimeException();
        }
    }

    private void validateLuhn(String cardNumber) {
        int sum = 0;
        boolean alternate = false;

        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }

        if (sum % 10 != 0){
            throw new RuntimeException("aaa");
        }
    }

    private void validateCardNumber(String cardNumber) {
        if (!cardNumber.matches("\\d+")) {
            throw new RuntimeException("bbb");
        }
        validateLuhn(cardNumber);
    }

    public String getCardType(String cardNumber) {
        if (cardNumber.startsWith("4")) {
            return "Visa";
        } else if (cardNumber.matches("^5[1-5].*")) {
            return "MasterCard";
        } else if (cardNumber.matches("^3[47].*")) {
            return "American Express";
        } else if (cardNumber.matches("^6(?:011|5|4[4-9]|22).*")) {
            return "Discover";
        } else if (cardNumber.matches("^35(?:2[89]|[3-8][0-9]).*")) {
            return "JCB";
        } else if (cardNumber.matches("^3(?:0[0-5]|[68]).*")) {
            return "Diners Club";
        } else {
            throw new RuntimeException("ccc");
        }
    }

    private void validateDate(String date){
        String regex = "^(0[1-9]|1[0-2])/\\d{2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);

        if (!matcher.matches()) {
            throw new RuntimeException("Data de expiração inválida");
        }

        String[] parts = date.split("/");
        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]);

        LocalDate currentDate = LocalDate.now();

        LocalDate expiry = LocalDate.of(2000 + year, month, 1).plusMonths(1);

        if(expiry.isBefore(currentDate)){
            throw new RuntimeException("Cartão expirado");
        }
    }


}
