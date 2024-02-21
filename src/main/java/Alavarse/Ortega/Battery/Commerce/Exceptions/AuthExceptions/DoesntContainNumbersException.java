package Alavarse.Ortega.Battery.Commerce.Exceptions.AuthExceptions;

public class DoesntContainNumbersException extends RuntimeException{

    public DoesntContainNumbersException() {
        super("Senha nao contem os numeros necessarios");
    }

    public DoesntContainNumbersException(String message) {
        super(message);
    }

}
