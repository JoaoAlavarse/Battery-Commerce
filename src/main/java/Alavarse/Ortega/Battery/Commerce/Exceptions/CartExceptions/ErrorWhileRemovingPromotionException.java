package Alavarse.Ortega.Battery.Commerce.Exceptions.CartExceptions;

public class ErrorWhileRemovingPromotionException extends RuntimeException{
    public ErrorWhileRemovingPromotionException() {
        super("Erro ao remover promoção");
    }

    public ErrorWhileRemovingPromotionException(String message) {
        super(message);
    }
}
