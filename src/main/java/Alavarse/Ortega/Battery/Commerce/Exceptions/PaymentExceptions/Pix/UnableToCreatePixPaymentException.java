package Alavarse.Ortega.Battery.Commerce.Exceptions.PaymentExceptions.Pix;

public class UnableToCreatePixPaymentException extends RuntimeException{
    public UnableToCreatePixPaymentException() {
        super("Não foi possível gerar a cobrança do tipo Pix");
    }
}
