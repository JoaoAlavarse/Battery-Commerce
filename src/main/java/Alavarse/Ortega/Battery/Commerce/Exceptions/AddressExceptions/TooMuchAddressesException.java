package Alavarse.Ortega.Battery.Commerce.Exceptions.AddressExceptions;

public class TooMuchAddressesException extends RuntimeException {
    public TooMuchAddressesException() {
        super("O limite é de três endereços");
    }

    public TooMuchAddressesException(String message) {
        super(message);
    }
}
