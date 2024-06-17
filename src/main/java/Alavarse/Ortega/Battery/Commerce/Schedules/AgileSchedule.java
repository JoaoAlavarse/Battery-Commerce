package Alavarse.Ortega.Battery.Commerce.Schedules;

import Alavarse.Ortega.Battery.Commerce.Services.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AgileSchedule {
    @Autowired
    private UtilsService service;

    @Scheduled(cron = "0 0 00 * * ?")
    public void refreshAgileToken(){
        this.service.loginAgilePay();
    }
}
