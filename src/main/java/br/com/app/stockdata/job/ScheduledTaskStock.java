package br.com.app.stockdata.job;

import br.com.app.stockdata.service.StockService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@AllArgsConstructor
public class ScheduledTaskStock {

    private final StockService stockService;

    //@Scheduled(cron = "0 0/5 9-17 ? * MON-FRI")
    @Scheduled(cron = "0 0/2 9-17 ? * SAT-SUN")
    public void myScheduledMethod() {
        System.out.println("Current hour: " + LocalTime.now());
    }
}
