package Alavarse.Ortega.Battery.Commerce.Exceptions.CardExceptions;

public class UnknownCardFlagException extends RuntimeException{
    public UnknownCardFlagException() {
        super("Bandeira não reconhecida pelo sistema");
    }

    public UnknownCardFlagException(String message) {
        super(message);
    }
}
