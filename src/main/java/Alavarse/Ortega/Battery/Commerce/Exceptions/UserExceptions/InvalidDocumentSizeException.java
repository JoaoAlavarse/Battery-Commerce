package Alavarse.Ortega.Battery.Commerce.Exceptions.UserExceptions;

public class InvalidDocumentSizeException extends RuntimeException{
    public InvalidDocumentSizeException() {
        super("O cpf deve conter 12 caracteres");
    }

    public InvalidDocumentSizeException(String message) {
        super(message);
    }
}
