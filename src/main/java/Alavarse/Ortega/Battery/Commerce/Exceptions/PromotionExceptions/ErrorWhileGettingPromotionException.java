package Alavarse.Ortega.Battery.Commerce.Exceptions.PromotionExceptions;

public class ErrorWhileGettingPromotionException extends RuntimeException{
    public ErrorWhileGettingPromotionException(String message) {
        super(message);
    }

    public ErrorWhileGettingPromotionException() {
        super("Erro ao salvar promoção");


    }
}
