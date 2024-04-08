package Alavarse.Ortega.Battery.Commerce.Exceptions.CartExceptions;

public class ErrorWhileGettingCartException extends RuntimeException{
    public ErrorWhileGettingCartException() {
        super("Erro ao carregar carrinho");
    }

    public ErrorWhileGettingCartException(String message) {
        super(message);
    }
}
