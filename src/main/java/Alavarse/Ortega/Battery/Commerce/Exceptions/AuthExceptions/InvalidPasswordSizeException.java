package Alavarse.Ortega.Battery.Commerce.Exceptions.AuthExceptions;

public class InvalidPasswordSizeException extends RuntimeException{
    public InvalidPasswordSizeException() {
        super("Tamanho da senha invalido");
    }

    public InvalidPasswordSizeException(String message) {
        super(message);
    }
}
