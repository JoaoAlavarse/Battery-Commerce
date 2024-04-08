package Alavarse.Ortega.Battery.Commerce.Exceptions.CartExceptions;

public class ErrorWhileSavingCartException extends RuntimeException{
    public ErrorWhileSavingCartException() {
        super("Erro ao salvar o carrinho");
    }

    public ErrorWhileSavingCartException(String message) {
        super(message);
    }
}
