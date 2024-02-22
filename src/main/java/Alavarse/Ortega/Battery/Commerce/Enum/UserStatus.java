package Alavarse.Ortega.Battery.Commerce.Enum;

public enum UserStatus {
    ACTIVE("active"),
    INACTIVE("inactive");

    private final String status;

    UserStatus(String status) {
        this.status = status;
    }

    private String getStatus(){return status;}
}
