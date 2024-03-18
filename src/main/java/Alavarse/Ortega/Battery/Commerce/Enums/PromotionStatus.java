package Alavarse.Ortega.Battery.Commerce.Enums;

public enum PromotionStatus {
    ACTIVE("active"),
    INACTIVE("inactive"),
    EXPIRED("expired");

    private final String status;

    PromotionStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
