package Alavarse.Ortega.Battery.Commerce.Exceptions.DeliveryExceptions;

public class ReversedStatusException extends RuntimeException{
    public ReversedStatusException() {
        super("O status da entrega não pode ser regredido");
    }

    public ReversedStatusException(String message) {
        super(message);
    }
}
