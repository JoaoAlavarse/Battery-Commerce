package Alavarse.Ortega.Battery.Commerce.Exceptions.AuthExceptions;

public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException() {
        super("Email ja cadastrado");
    }

    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
