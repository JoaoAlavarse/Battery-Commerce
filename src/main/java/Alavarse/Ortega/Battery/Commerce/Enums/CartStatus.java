package Alavarse.Ortega.Battery.Commerce.Enums;

public enum CartStatus {
    OPENED("opened"),
    CLOSED("closed"),
    CANCELLED("cancelled");

    public final String status;

    CartStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
