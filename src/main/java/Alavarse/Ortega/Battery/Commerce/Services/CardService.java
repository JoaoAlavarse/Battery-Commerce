package Alavarse.Ortega.Battery.Commerce.Services;

import Alavarse.Ortega.Battery.Commerce.DTOs.CardDTO;
import Alavarse.Ortega.Battery.Commerce.DTOs.CardResponseDTO;
import Alavarse.Ortega.Battery.Commerce.DTOs.UpdateCardDTO;
import Alavarse.Ortega.Battery.Commerce.Entities.CardEntity;
import Alavarse.Ortega.Battery.Commerce.Exceptions.CardExceptions.*;
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
            return new CardResponseDTO(makeCardNumberResponse(newCard.getCardNumber()), newCard.getFlag(), newCard.getCardOwner(), newCard.getExpirationDate());
        } catch (Exception e){
            throw new ErrorWhileSavingCardException();
        }
    }

    private String makeCardNumberResponse(String encryptedCardNumber){
        String decryptedCardNumber = encryptService.decrypt(encryptedCardNumber);
        return decryptedCardNumber.substring(decryptedCardNumber.length() - 4);
    }

    public void setCardMainTrue(String id){
        CardEntity card = this.repository.findById(id).orElseThrow(ErrorWhileGettingCardException::new);
        setCardMainFalse(card.getUser().getUserId());
        try {
            card.setMain(true);
            repository.save(card);
        } catch (Exception e){
            throw new ErrorWhileSavingCardException();
        }
    }

    private void setCardMainFalse(String id){
        List<CardEntity> list = this.repository.findByUser(this.userService.findById(id));
        try {
            list.forEach(cardEntity -> {
                cardEntity.setMain(false);
                this.repository.save(cardEntity);
            });
        } catch (Exception e){
            throw new ErrorWhileSavingCardException();
        }
    }

    private void verifyCardQuantity(String userId){
        if (this.repository.findByUser(this.userService.findById(userId)).size() >= 3){
            throw new TooManyCardsException();
        }
    }

    public List<CardResponseDTO> getAllByUser(String userId){
        List<CardResponseDTO> list = new ArrayList<>();
        try {
            this.repository.findByUser(this.userService.findById(userId)).forEach(cardEntity -> {
                list.add(new CardResponseDTO(this.makeCardNumberResponse(cardEntity.getCardNumber()), this.encryptService.decrypt(cardEntity.getFlag()),
                        this.encryptService.decrypt(cardEntity.getCardOwner()), this.encryptService.decrypt(cardEntity.getExpirationDate())));
            });
            return list;
        } catch (Exception e){
            throw new ErrorWhileGettingCardException();
        }
    }

    public CardResponseDTO updateCard(UpdateCardDTO data, String cardId){
        CardEntity card = this.repository.findById(cardId).orElseThrow(RuntimeException::new);
        this.validateDate(data.expirationDate());
        try {
            card.setCardOwner(this.encryptService.encrypt(data.cardOwner()));
            card.setExpirationDate(this.encryptService.encrypt(data.expirationDate()));
            repository.save(card);
            return new CardResponseDTO(this.makeCardNumberResponse(card.getCardNumber()), this.encryptService.decrypt(card.getFlag()),
                    this.encryptService.decrypt(card.getCardOwner()), this.encryptService.decrypt(card.getExpirationDate()));
        } catch (Exception e){
            throw new ErrorWhileSavingCardException();
        }
    }

    public void delete(String cardId){
        try{
            this.repository.deleteById(cardId);
        } catch (Exception e){
            throw new ErrorWhileSavingCardException();
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
            throw new InvalidCardNumberException();
        }
    }

    private void validateCardNumber(String cardNumber) {
        if (!cardNumber.matches("\\d+")) {
            throw new InvalidCardNumberFormatException();
        }
        validateLuhn(cardNumber);
    }

    public String getCardType(String cardNumber) {
        if (cardNumber.matches("^4[0-9]{12}(?:[0-9]{3})?$")) {
            return "Visa";
        } else if (cardNumber.matches("^5[1-5][0-9]{14}$")) {
            return "MasterCard";
        } else if (cardNumber.matches("^3[47][0-9]{13}$")) {
            return "American Express";
        } else if (cardNumber.matches("^6(?:011|5[0-9]{2})[0-9]{12}$")) {
            return "Discover";
        } else if (cardNumber.matches("^4011(78|79)|^43(1274|8935)|^45(1416|7393|763(1|2))|^50(4175|6699|67[0-6][0-9]" +
                "|677[0-8]|9[0-8][0-9]{2}|99[0-8][0-9]|999[0-9])|^627780|^63(6297|6368|6369)|^65(0(0(3([1-3]|[5-9])|4([0-9])|5[0-1])|4(0[5-9]|" +
                "[1-3][0-9]|8[5-9]|9[0-9])|5([0-2][0-9]|3[0-8]|4[1-9]|[5-8][0-9]|9[0-8])|7(0[0-9]|1[0-8]|2[0-7])|9(0[1-9]|[1-6][0-9]" +
                "|7[0-8]))|16(5[2-9]|[6-7][0-9])|50(0[0-9]|1[0-9]|2[1-9]|[3-4][0-9]|5[0-8]))")) {
            return "Elo";
        } else if (cardNumber.matches("^(?:2131|1800|35\\d{3})\\d{11}")) {
            return "JCB";
        } else if (cardNumber.matches("^3(?:0[0-5]|[68]).*")) {
            return "Diners Club";
        } else if  (cardNumber.matches("^((?!504175))^((?!5067))(^50[0-9])")){
            return "Aura";
        } else if  (cardNumber.matches("^606282|^3841(?:[0|4|6]{1})0/")){
            return "HiperCard";
        } else {
            throw new UnknownCardFlagException();
        }
    }

    private void validateDate(String date){
        String regex = "^(0[1-9]|1[0-2])/\\d{2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);

        if (!matcher.matches()) {
            throw new InvalidDateFormatException();
        }

        String[] parts = date.split("/");
        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]);

        LocalDate currentDate = LocalDate.now();

        LocalDate expiry = LocalDate.of(2000 + year, month, 1).plusMonths(1);

        if(expiry.isBefore(currentDate)){
            throw new ExpiredCardException();
        }
    }


}
