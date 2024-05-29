package Alavarse.Ortega.Battery.Commerce.Exceptions.CardExceptions;

public class TooManyCardsException extends RuntimeException{
    public TooManyCardsException() {
        super("Limite de três cartões por usuário");
    }

    public TooManyCardsException(String message) {
        super(message);
    }
}
