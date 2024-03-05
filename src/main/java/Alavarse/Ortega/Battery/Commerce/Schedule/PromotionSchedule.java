package Alavarse.Ortega.Battery.Commerce.Schedule;

import Alavarse.Ortega.Battery.Commerce.Service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PromotionSchedule {
    @Autowired
    private PromotionService service;

    @Scheduled(cron = "0 0 00 * * ?")
    public void autoSetExpired(){
        service.autoExpire();
    }

}
