package Alavarse.Ortega.Battery.Commerce.Exceptions.CartExceptions;

public class ErrorWhileAddingPromotionException extends RuntimeException{
    public ErrorWhileAddingPromotionException() {
        super("Erro ao adicionar promoção");
    }

    public ErrorWhileAddingPromotionException(String message) {
        super(message);
    }
}
