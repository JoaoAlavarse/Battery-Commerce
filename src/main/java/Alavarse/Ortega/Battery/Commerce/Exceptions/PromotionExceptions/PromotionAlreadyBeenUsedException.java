package Alavarse.Ortega.Battery.Commerce.Exceptions.PromotionExceptions;

public class PromotionAlreadyBeenUsedException extends RuntimeException{
    public PromotionAlreadyBeenUsedException() {
        super("Promoção já utilizada pelo usuário");
    }

    public PromotionAlreadyBeenUsedException(String message) {
        super(message);
    }
}
