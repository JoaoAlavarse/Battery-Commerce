package Alavarse.Ortega.Battery.Commerce.Exceptions.UserExceptions;

public class InvalidDocumentSizeException extends RuntimeException{
    public InvalidDocumentSizeException() {
        super("O CPF deve conter 14 caracteres");
    }

    public InvalidDocumentSizeException(String message) {
        super(message);
    }
}
