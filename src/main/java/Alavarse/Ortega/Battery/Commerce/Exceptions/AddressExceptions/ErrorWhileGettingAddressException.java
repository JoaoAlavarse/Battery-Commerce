package Alavarse.Ortega.Battery.Commerce.Exceptions.AddressExceptions;

public class ErrorWhileGettingAddressException extends RuntimeException{
    public ErrorWhileGettingAddressException() {
        super("Erro ao recuperar endereços");
    }

    public ErrorWhileGettingAddressException(String message) {
        super(message);
    }
}
