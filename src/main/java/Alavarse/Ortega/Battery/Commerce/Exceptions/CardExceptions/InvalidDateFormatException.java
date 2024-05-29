package Alavarse.Ortega.Battery.Commerce.Exceptions.CardExceptions;

public class InvalidDateFormatException extends RuntimeException{
    public InvalidDateFormatException() {
        super("Formato incorreto para data. Experado: MM/yy");
    }

    public InvalidDateFormatException(String message) {
        super(message);
    }
}
