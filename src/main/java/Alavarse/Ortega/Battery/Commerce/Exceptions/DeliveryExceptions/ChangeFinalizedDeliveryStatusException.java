package Alavarse.Ortega.Battery.Commerce.Exceptions.DeliveryExceptions;

public class ChangeFinalizedDeliveryStatusException extends RuntimeException{
    public ChangeFinalizedDeliveryStatusException() {
        super("Uma entrega finalizada não pode ter seu status alterado");
    }

    public ChangeFinalizedDeliveryStatusException(String message) {
        super(message);
    }
}
