package Alavarse.Ortega.Battery.Commerce.Exceptions.UserExceptions;

public class ErrorWhileGettingUsersException extends RuntimeException{
    public ErrorWhileGettingUsersException() {
        super("Erro ao recuperar usuarios");
    }

    public ErrorWhileGettingUsersException(String message) {
        super(message);
    }
}
