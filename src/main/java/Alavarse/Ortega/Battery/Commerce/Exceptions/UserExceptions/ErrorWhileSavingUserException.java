package Alavarse.Ortega.Battery.Commerce.Exceptions.UserExceptions;

public class ErrorWhileSavingUserException extends RuntimeException{
    public ErrorWhileSavingUserException() {
        super("Erro ao salvar usuario");
    }

    public ErrorWhileSavingUserException(String message) {
        super(message);
    }
}
