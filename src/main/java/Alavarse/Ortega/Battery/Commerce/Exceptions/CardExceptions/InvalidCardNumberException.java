package Alavarse.Ortega.Battery.Commerce.Exceptions.CardExceptions;

public class InvalidCardNumberException extends RuntimeException{
    public InvalidCardNumberException() {
        super("Número de cartão inválido");
    }

    public InvalidCardNumberException(String message) {
        super(message);
    }
}
