package Alavarse.Ortega.Battery.Commerce.Exceptions.AuthExceptions;

public class InconsistentPasswordsException extends RuntimeException {
    public InconsistentPasswordsException() {
        super("Senhas imcompatíveis");
    }

    public InconsistentPasswordsException(String message) {
        super(message);
    }
}
