package Alavarse.Ortega.Battery.Commerce.Exceptions.DeliveryExceptions;

public class SameStatusException extends RuntimeException{
    public SameStatusException() {
        super("O status solicitado não pode ser o atual");
    }

    public SameStatusException(String message) {
        super(message);
    }
}
