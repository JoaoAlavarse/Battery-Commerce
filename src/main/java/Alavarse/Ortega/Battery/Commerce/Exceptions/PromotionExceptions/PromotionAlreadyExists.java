package Alavarse.Ortega.Battery.Commerce.Exceptions.PromotionExceptions;

public class PromotionAlreadyExists extends RuntimeException {
    public PromotionAlreadyExists() {
        super("Promocao ja existente");
    }

    public PromotionAlreadyExists(String message) {
        super(message);
    }
}
