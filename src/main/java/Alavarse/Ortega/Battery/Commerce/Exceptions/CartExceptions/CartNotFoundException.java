package Alavarse.Ortega.Battery.Commerce.Exceptions.CartExceptions;

public class CartNotFoundException extends RuntimeException{
    public CartNotFoundException() {
        super("Carrinho não encontrado");
    }

    public CartNotFoundException(String message) {
        super(message);
    }
}
