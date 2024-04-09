package Alavarse.Ortega.Battery.Commerce.Exceptions.AuthExceptions;

public class DoesntContainNumbersException extends RuntimeException{

    public DoesntContainNumbersException() {
        super("Senha não contém os números necessários");
    }

    public DoesntContainNumbersException(String message) {
        super(message);
    }

}
