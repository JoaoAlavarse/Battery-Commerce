package Alavarse.Ortega.Battery.Commerce.Exceptions.AddressExceptions;

public class AddressNotFoundException extends RuntimeException{
    public AddressNotFoundException() {
        super("Endereço Não Encontrado");
    }

    public AddressNotFoundException(String message) {
        super(message);
    }
}
