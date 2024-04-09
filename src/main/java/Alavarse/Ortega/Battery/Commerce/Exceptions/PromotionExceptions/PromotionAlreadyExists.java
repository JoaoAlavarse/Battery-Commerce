package Alavarse.Ortega.Battery.Commerce.Exceptions.PromotionExceptions;

public class PromotionAlreadyExists extends RuntimeException {
    public PromotionAlreadyExists() {
        super("Promoção já existente");
    }

    public PromotionAlreadyExists(String message) {
        super(message);
    }
}
