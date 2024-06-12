package Alavarse.Ortega.Battery.Commerce.Exceptions.DeliveryExceptions;

public class ErrorWhileSavingDeliveryException extends RuntimeException {
    public ErrorWhileSavingDeliveryException() {
        super("Erro ao salvar entrega");
    }

    public ErrorWhileSavingDeliveryException(String message) {
        super(message);
    }
}