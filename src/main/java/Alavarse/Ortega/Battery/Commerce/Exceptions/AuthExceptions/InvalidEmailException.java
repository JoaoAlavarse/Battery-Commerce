package Alavarse.Ortega.Battery.Commerce.Exceptions.AuthExceptions;

public class InvalidEmailException extends RuntimeException{

    public InvalidEmailException() {
        super("E-mail Invalido");
    }

    public InvalidEmailException(String message) {
        super(message);
    }
}
