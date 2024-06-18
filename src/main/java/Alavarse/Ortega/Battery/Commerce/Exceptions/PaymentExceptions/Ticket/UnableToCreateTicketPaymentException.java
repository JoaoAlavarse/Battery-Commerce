package Alavarse.Ortega.Battery.Commerce.Exceptions.PaymentExceptions.Ticket;

public class UnableToCreateTicketPaymentException extends RuntimeException{
    public UnableToCreateTicketPaymentException() {
        super("Não foi possível gerar a cobrança do tipo Boleto");
    }

    public UnableToCreateTicketPaymentException(String message) {
        super(message);
    }
}
