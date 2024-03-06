package Alavarse.Ortega.Battery.Commerce.Exceptions.PromotionExceptions;

public class PromotionNotFoundException extends RuntimeException{
    public PromotionNotFoundException() {
        super("Promoção não encontrada");
    }

    public PromotionNotFoundException(String message) {
        super(message);
    }
}
