package Alavarse.Ortega.Battery.Commerce.Exceptions.CartExceptions;

public class InsufficientBatteriesException extends RuntimeException{
    public InsufficientBatteriesException(int quantity, String batteryName) {
        super("Estoque de " + batteryName + " insuficiente para serem adicionadas" + quantity);
    }

    public InsufficientBatteriesException(String message) {
        super(message);
    }
}
