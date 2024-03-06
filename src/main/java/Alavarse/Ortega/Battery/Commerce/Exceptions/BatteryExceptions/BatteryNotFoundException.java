package Alavarse.Ortega.Battery.Commerce.Exceptions.BatteryExceptions;

public class BatteryNotFoundException extends RuntimeException{
    public BatteryNotFoundException() {
        super("Bateria não encontrada");
    }

    public BatteryNotFoundException(String message) {
        super(message);
    }
}
