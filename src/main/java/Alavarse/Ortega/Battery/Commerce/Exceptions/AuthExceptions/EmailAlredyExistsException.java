package Alavarse.Ortega.Battery.Commerce.Exceptions.AuthExceptions;

public class EmailAlredyExistsException extends RuntimeException{
    public EmailAlredyExistsException() {
        super("Email ja cadastrado");
    }

    public EmailAlredyExistsException(String message) {
        super(message);
    }
}
