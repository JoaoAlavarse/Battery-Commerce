package Alavarse.Ortega.Battery.Commerce.Exceptions.PromotionExceptions;

public class InvalidExpirationDateException extends RuntimeException{
    public InvalidExpirationDateException() {
        super("Data enviada inferior à atual");
    }

    public InvalidExpirationDateException(String message) {
        super(message);
    }
}
