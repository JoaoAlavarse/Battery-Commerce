package Alavarse.Ortega.Battery.Commerce.Exceptions.UserExceptions;

public class ErrorWhileChangingRolesException extends RuntimeException{
    public ErrorWhileChangingRolesException() {
        super("O usuário já possuí o cargo selecionado");
    }

    public ErrorWhileChangingRolesException(String message) {
        super(message);
    }
}
