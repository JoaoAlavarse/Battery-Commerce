package Alavarse.Ortega.Battery.Commerce.Schedules;

import Alavarse.Ortega.Battery.Commerce.Services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CartSchedule {
    @Autowired
    private CartService service;

    @Scheduled(cron = "0 0 00 * * ?")
    public void autoCancel(){service.autoCancel();}
}
