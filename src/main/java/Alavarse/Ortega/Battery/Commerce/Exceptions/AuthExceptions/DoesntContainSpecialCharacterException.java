package Alavarse.Ortega.Battery.Commerce.Exceptions.AuthExceptions;

public class DoesntContainSpecialCharacterException extends RuntimeException{
    public DoesntContainSpecialCharacterException() {
        super("Senha nao contem caracteres especiais");
    }

    public DoesntContainSpecialCharacterException(String message) {
        super(message);
    }
}
