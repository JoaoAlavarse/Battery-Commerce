package Alavarse.Ortega.Battery.Commerce.Exceptions.UserExceptions;

public class SameUserRoleChangeException extends RuntimeException{
    public SameUserRoleChangeException() {
        super("Usuário logado não pode alterar o próprio cargo");
    }

    public SameUserRoleChangeException(String message) {
        super(message);
    }
}
