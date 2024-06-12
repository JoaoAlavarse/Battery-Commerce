package Alavarse.Ortega.Battery.Commerce.Enums;

public enum DeliveryStatus {
    CONFIRMADO("confirmado"),
    PREPARANDO("preparando"),
    TRANSITO("transito"),
    ENTREGUE("entregue");

    private final String status;

    DeliveryStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
