package Alavarse.Ortega.Battery.Commerce.Exceptions.CartExceptions;

public class InsufficientBatteriesToAddException extends RuntimeException{
    public InsufficientBatteriesToAddException(int quantity) {
        super("Número de baterias insuficiente para serem adicionadas" + quantity);
    }

    public InsufficientBatteriesToAddException(String message) {
        super(message);
    }
}
