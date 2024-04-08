package Alavarse.Ortega.Battery.Commerce.Exceptions.CartExceptions;

public class ErrorWhileCalculatingCartTotalValueException extends RuntimeException{
    public ErrorWhileCalculatingCartTotalValueException() {
        super("Erro ao calcular o valor total do carrinho");
    }

    public ErrorWhileCalculatingCartTotalValueException(String message) {
        super(message);
    }
}
