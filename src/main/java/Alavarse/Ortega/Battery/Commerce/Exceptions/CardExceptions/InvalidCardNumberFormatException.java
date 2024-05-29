package Alavarse.Ortega.Battery.Commerce.Exceptions.CardExceptions;

public class InvalidCardNumberFormatException extends RuntimeException{
    public InvalidCardNumberFormatException() {
        super("Formato do dado informado inválido");
    }

    public InvalidCardNumberFormatException(String message) {
        super(message);
    }
}
