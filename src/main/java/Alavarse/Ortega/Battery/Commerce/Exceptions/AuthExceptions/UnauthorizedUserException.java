package Alavarse.Ortega.Battery.Commerce.Exceptions.AuthExceptions;

public class UnauthorizedUserException extends RuntimeException {
    public UnauthorizedUserException() {
        super("Usuário não autorizado");
    }

    public UnauthorizedUserException(String message) {
        super(message);
    }
}
