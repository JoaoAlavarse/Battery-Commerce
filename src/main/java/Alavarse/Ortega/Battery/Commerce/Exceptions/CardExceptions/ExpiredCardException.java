package Alavarse.Ortega.Battery.Commerce.Exceptions.CardExceptions;

public class ExpiredCardException extends RuntimeException{
    public ExpiredCardException() {
        super("Cartão com data de validade expirada");
    }

    public ExpiredCardException(String message) {
        super(message);
    }
}
