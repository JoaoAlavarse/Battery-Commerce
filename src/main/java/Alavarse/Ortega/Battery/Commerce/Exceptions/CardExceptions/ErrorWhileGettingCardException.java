package Alavarse.Ortega.Battery.Commerce.Exceptions.CardExceptions;

public class ErrorWhileGettingCardException extends RuntimeException{
    public ErrorWhileGettingCardException() {
        super("Erro ao recuperar cartão");
    }

    public ErrorWhileGettingCardException(String message) {
        super(message);
    }
}
