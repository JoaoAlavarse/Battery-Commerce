package Alavarse.Ortega.Battery.Commerce.Exceptions.AuthExceptions;

public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException() {
        super("Senha inválida");
    }

    public InvalidPasswordException(String message) {
        super(message);
    }
}
