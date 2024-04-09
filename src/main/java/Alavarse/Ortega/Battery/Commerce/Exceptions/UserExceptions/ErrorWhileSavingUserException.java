package Alavarse.Ortega.Battery.Commerce.Exceptions.UserExceptions;

public class ErrorWhileSavingUserException extends RuntimeException{
    public ErrorWhileSavingUserException() {
        super("Erro ao salvar usuário");
    }

    public ErrorWhileSavingUserException(String message) {
        super(message);
    }
}
