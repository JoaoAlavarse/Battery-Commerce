package Alavarse.Ortega.Battery.Commerce.Exceptions.BatteryExceptions;

public class ErrorWhileGettingBatteryException extends RuntimeException{
    public ErrorWhileGettingBatteryException() {
        super("Erro ao recuperar bateria");
    }

    public ErrorWhileGettingBatteryException(String message) {
        super(message);
    }
}
