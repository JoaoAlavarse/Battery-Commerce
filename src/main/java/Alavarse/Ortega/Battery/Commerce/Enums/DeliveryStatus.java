package Alavarse.Ortega.Battery.Commerce.Enums;

public enum DeliveryStatus {
    CONFIRMANDO("confirmando"),
    CONFIRMADO("confirmado"),
    PREPARANDO("preparando"),
    TRANSITO("transito"),
    ENTREGUE("entregue"),
    CANCELADO("cancelado");

    private final String status;

    DeliveryStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
