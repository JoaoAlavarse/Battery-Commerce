package Alavarse.Ortega.Battery.Commerce.Enums;

public enum BatteryStatus {
    ACTIVE("active"),
    INACTIVE("inactive");


    private final String status;

    BatteryStatus(String status) {
        this.status = status;
    }

    private String getStatu(){return status;}
}
