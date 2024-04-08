package Alavarse.Ortega.Battery.Commerce.Exceptions.CartExceptions;

public class BatteryAlreadyExistsInException extends RuntimeException{
    public BatteryAlreadyExistsInException() {
        super("A bateria selecionada já foi adicionada ao carrinho");
    }

    public BatteryAlreadyExistsInException(String message) {
        super(message);
    }
}
