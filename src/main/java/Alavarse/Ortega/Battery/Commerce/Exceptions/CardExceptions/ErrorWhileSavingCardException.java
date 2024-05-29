package Alavarse.Ortega.Battery.Commerce.Exceptions.CardExceptions;

public class ErrorWhileSavingCardException extends RuntimeException{
    public ErrorWhileSavingCardException() {
        super("Erro ao salvar cartão");
    }

    public ErrorWhileSavingCardException(String message) {
        super(message);
    }
}
