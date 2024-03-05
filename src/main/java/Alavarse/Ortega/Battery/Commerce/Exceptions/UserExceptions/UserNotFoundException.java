package Alavarse.Ortega.Battery.Commerce.Exceptions.UserExceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super("Usuario nao encontrado");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
