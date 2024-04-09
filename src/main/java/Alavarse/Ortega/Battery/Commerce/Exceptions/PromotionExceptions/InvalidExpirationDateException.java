package Alavarse.Ortega.Battery.Commerce.Exceptions.PromotionExceptions;

public class InvalidExpirationDateException extends RuntimeException{
    public InvalidExpirationDateException() {
        super("Data invalida");
    }

    public InvalidExpirationDateException(String message) {
        super(message);
    }
}
