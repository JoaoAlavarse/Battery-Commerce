package Alavarse.Ortega.Battery.Commerce.Exceptions.PromotionExceptions;

public class ErrorWhileSavingPromotionException extends RuntimeException{
    public ErrorWhileSavingPromotionException() {
        super("Erro ao salvar promoção");
    }

    public ErrorWhileSavingPromotionException(String message) {
        super(message);
    }
}
