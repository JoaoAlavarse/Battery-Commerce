package Alavarse.Ortega.Battery.Commerce.Exceptions.UserExceptions;

public class InvalidDocumentException extends RuntimeException{
    public InvalidDocumentException() {
        super("CPF inválido");
    }

    public InvalidDocumentException(String message) {
        super(message);
    }
}
