package Alavarse.Ortega.Battery.Commerce.Exceptions.SaleExceptions;

public class ErrorWhileSavingSaleException extends RuntimeException{
    public ErrorWhileSavingSaleException() {
        super("Erro ao salvar venda");
    }

    public ErrorWhileSavingSaleException(String message) {
        super(message);
    }
}
