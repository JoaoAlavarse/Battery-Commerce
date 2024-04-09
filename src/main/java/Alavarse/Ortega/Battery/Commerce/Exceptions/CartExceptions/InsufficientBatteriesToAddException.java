package Alavarse.Ortega.Battery.Commerce.Exceptions.CartExceptions;

public class InsufficientBatteriesToAddException extends RuntimeException{
    public InsufficientBatteriesToAddException() {
        super("Número de baterias insuficiente para serem adicionadas");
    }

    public InsufficientBatteriesToAddException(String message) {
        super(message);
    }
}
