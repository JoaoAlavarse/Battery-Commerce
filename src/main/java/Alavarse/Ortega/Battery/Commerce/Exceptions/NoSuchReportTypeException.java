package Alavarse.Ortega.Battery.Commerce.Exceptions;

public class NoSuchReportTypeException extends RuntimeException {
    public NoSuchReportTypeException() {
        super("O tipode de relatório informado não existe");
    }

    public NoSuchReportTypeException(String message) {
        super(message);
    }
}
