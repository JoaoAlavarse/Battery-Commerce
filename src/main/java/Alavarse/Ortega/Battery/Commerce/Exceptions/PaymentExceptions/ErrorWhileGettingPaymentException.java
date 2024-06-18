package Alavarse.Ortega.Battery.Commerce.Exceptions.PaymentExceptions;

public class ErrorWhileGettingPaymentException extends RuntimeException{
    public ErrorWhileGettingPaymentException() {
        super("Ocorreu um erro ao consultar o pagamento");
    }

    public ErrorWhileGettingPaymentException(String message) {
        super(message);
    }
}
