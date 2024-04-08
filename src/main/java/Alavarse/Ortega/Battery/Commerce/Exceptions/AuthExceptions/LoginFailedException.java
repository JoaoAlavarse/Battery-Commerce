package Alavarse.Ortega.Battery.Commerce.Exceptions.AuthExceptions;

public class LoginFailedException extends RuntimeException{
    public LoginFailedException() {
        super("Senha ou Email invalido");
    }

    public LoginFailedException(String message) {
        super(message);
    }
}
