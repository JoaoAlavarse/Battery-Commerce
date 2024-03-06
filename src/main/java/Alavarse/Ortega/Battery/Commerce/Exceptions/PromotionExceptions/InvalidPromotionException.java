package Alavarse.Ortega.Battery.Commerce.Exceptions.PromotionExceptions;

public class InvalidPromotionException extends RuntimeException{
    public InvalidPromotionException() {
        super("Promoção inválida");
    }

    public InvalidPromotionException(String message) {
        super(message);
    }
}
