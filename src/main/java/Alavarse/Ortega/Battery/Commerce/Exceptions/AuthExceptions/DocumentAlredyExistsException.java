package Alavarse.Ortega.Battery.Commerce.Exceptions.AuthExceptions;

public class DocumentAlredyExistsException extends RuntimeException {
    public DocumentAlredyExistsException() {
        super("CPF ja cadastrado");
    }

    public DocumentAlredyExistsException(String message) {
        super(message);
    }
}
