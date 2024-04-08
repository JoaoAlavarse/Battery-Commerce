package Alavarse.Ortega.Battery.Commerce.Exceptions.CartExceptions;

public class BatteryDoenstExistsInException extends RuntimeException {
    public BatteryDoenstExistsInException() {
        super("A bateria selecionada não existe no carrinho");
    }

    public BatteryDoenstExistsInException(String message) {
        super(message);
    }
}
