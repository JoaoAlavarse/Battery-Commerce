package Alavarse.Ortega.Battery.Commerce.Exceptions.PaymentExceptions.Card;

public class UnableToCreateCardPaymentException extends RuntimeException{
    public UnableToCreateCardPaymentException() {
        super("Não foi possível gerar a cobrança");
    }
}
