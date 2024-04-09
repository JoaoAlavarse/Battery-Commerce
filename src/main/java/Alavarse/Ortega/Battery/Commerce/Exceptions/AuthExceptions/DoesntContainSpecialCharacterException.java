package Alavarse.Ortega.Battery.Commerce.Exceptions.AuthExceptions;

public class DoesntContainSpecialCharacterException extends RuntimeException{
    public DoesntContainSpecialCharacterException() {
        super("Senha não contém caracteres especiais");
    }

    public DoesntContainSpecialCharacterException(String message) {
        super(message);
    }
}
