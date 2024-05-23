package Alavarse.Ortega.Battery.Commerce.Exceptions.AddressExceptions;

public class AddressAlreadyExistsException extends RuntimeException {
    public AddressAlreadyExistsException() {
        super("CEP já cadastrado");
    }

    public AddressAlreadyExistsException(String message) {
        super(message);
    }
}
