package Alavarse.Ortega.Battery.Commerce.Exceptions.PaymentExceptions.Card;

public class UnableToMakeCardPaymentException extends RuntimeException{
    public UnableToMakeCardPaymentException() {
        super("Não foi possível realizar o pagamento via cartão de crédito");
    }
}
