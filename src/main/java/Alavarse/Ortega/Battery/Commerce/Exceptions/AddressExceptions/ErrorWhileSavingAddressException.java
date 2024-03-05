package Alavarse.Ortega.Battery.Commerce.Exceptions.AddressExceptions;

public class ErrorWhileSavingAddressException extends RuntimeException{
    public ErrorWhileSavingAddressException() {
        super("Erro ao salvar endereços");
    }

    public ErrorWhileSavingAddressException(String message) {
        super(message);
    }
}
