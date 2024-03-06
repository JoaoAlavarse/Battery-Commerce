package Alavarse.Ortega.Battery.Commerce.Exceptions.BatteryExceptions;

public class ErrorWhileSavingBatteryException extends RuntimeException{
    public ErrorWhileSavingBatteryException() {
        super("Erro ao salvar bateria");
    }

    public ErrorWhileSavingBatteryException(String message) {
        super(message);
    }
}
