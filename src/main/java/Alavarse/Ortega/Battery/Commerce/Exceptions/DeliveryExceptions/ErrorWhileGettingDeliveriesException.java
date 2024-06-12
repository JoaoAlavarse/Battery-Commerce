package Alavarse.Ortega.Battery.Commerce.Exceptions.DeliveryExceptions;

public class ErrorWhileGettingDeliveriesException extends RuntimeException {
    public ErrorWhileGettingDeliveriesException() {
        super("Erro ao recuperar entregas");
    }

    public ErrorWhileGettingDeliveriesException(String message) {
        super(message);
    }
}
