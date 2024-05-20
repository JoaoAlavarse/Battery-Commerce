package Alavarse.Ortega.Battery.Commerce.Exceptions.UserExceptions;

public class ErrorGettingUserReportData extends RuntimeException {
    public ErrorGettingUserReportData() {
        super("Erro ao carregar dados de usuários para relatório");
    }

    public ErrorGettingUserReportData(String message) {
        super(message);
    }
}
