package Alavarse.Ortega.Battery.Commerce.Exceptions.PromotionExceptions;

public class InvalidExpirationDateException extends RuntimeException{
    public InvalidExpirationDateException() {
        super("Data Invalida");
    }

    public InvalidExpirationDateException(String message) {
        super(message);
    }
}
