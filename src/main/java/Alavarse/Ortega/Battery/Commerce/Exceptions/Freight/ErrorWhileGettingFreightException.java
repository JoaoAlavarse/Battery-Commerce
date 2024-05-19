package Alavarse.Ortega.Battery.Commerce.Exceptions.Freight;

public class ErrorWhileGettingFreightException extends RuntimeException{
    public ErrorWhileGettingFreightException() {
        super("Um erro ocorreu ao calcular o frete");
    }

    public ErrorWhileGettingFreightException(String message) {
        super(message);
    }
}
