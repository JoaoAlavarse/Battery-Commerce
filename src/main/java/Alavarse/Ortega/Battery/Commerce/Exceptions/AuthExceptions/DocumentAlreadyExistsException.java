package Alavarse.Ortega.Battery.Commerce.Exceptions.AuthExceptions;

public class DocumentAlreadyExistsException extends RuntimeException {
    public DocumentAlreadyExistsException() {
        super("CPF já cadastrado");
    }

    public DocumentAlreadyExistsException(String message) {
        super(message);
    }
}
