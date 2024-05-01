package Alavarse.Ortega.Battery.Commerce.Exceptions.BatteryExceptions;

public class BatteryAlreadyExistsException extends RuntimeException{
    public BatteryAlreadyExistsException() {
        super("Código de bateria já cadastrado");
    }

    public BatteryAlreadyExistsException(String message) {
        super(message);
    }
}
