package Alavarse.Ortega.Battery.Commerce.Enums;

public enum PaymentStatus {
    PENDENTE("pendente"),
    PAGO("pago");

    private final String status;

    PaymentStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
