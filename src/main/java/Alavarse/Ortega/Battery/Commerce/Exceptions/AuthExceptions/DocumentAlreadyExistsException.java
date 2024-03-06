package Alavarse.Ortega.Battery.Commerce.Exceptions.AuthExceptions;

public class DocumentAlreadyExistsException extends RuntimeException {
    public DocumentAlreadyExistsException() {
        super("CPF ja cadastrado");
    }

    public DocumentAlreadyExistsException(String message) {
        super(message);
    }
}
