package Alavarse.Ortega.Battery.Commerce.Exceptions.AuthExceptions;

public class LoginFailedException extends RuntimeException{
    public LoginFailedException() {
        super("Senha ou Email inválidos");
    }

    public LoginFailedException(String message) {
        super(message);
    }
}
