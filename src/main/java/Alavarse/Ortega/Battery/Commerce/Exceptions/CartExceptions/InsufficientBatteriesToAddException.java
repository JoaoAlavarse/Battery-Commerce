package Alavarse.Ortega.Battery.Commerce.Exceptions.CartExceptions;

public class InsufficientBatteriesToAddException extends RuntimeException{
    public InsufficientBatteriesToAddException() {
        super("Numero de baterias insuficiente para serem adicionadas");
    }

    public InsufficientBatteriesToAddException(String message) {
        super(message);
    }
}
