package Alavarse.Ortega.Battery.Commerce.Exceptions.AuthExceptions;

public class InvalidPasswordSizeException extends RuntimeException{
    public InvalidPasswordSizeException() {
        super("Tamanho da senha inválido. Mínimo 12 caracteres");
    }

    public InvalidPasswordSizeException(String message) {
        super(message);
    }
}
