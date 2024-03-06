package Alavarse.Ortega.Battery.Commerce.Exceptions.PromotionExceptions;

public class PromotionAlredyBeenUsedException extends RuntimeException{
    public PromotionAlredyBeenUsedException() {
        super("Promoção já utilizada pelo usuário");
    }

    public PromotionAlredyBeenUsedException(String message) {
        super(message);
    }
}
