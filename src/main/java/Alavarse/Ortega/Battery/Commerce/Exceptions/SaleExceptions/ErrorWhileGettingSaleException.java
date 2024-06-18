package Alavarse.Ortega.Battery.Commerce.Exceptions.SaleExceptions;

public class ErrorWhileGettingSaleException extends RuntimeException{
    public ErrorWhileGettingSaleException() {
        super("Erro ao retornar as vendas");
    }

    public ErrorWhileGettingSaleException(String message) {
        super(message);
    }
}
