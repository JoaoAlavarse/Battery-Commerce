package Alavarse.Ortega.Battery.Commerce.Enum;

public enum BatteryStatus {
    ACTIVE("active"),
    INACTIVE("inactive");


    private final String status;

    BatteryStatus(String status) {
        this.status = status;
    }

    private String getStatu(){return status;}
}
