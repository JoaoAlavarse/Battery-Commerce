package Alavarse.Ortega.Battery.Commerce.Exceptions.UserExceptions;

public class InvalidDocumentException extends RuntimeException{
    public InvalidDocumentException() {
        super("CPF invalido");
    }

    public InvalidDocumentException(String message) {
        super(message);
    }
}
