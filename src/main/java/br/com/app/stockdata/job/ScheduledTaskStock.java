package br.com.app.stockdata.job;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@AllArgsConstructor
public class ScheduledTaskStock {

    @Scheduled(cron = "0 0/2 9-17 ? * SAT-SUN")
    public void ScheduleFetchStock() {
        logger.log("My Message1");    }

    @Scheduled(cron = "0 0/2 9-17 ? * SAT-SUN")
    public void ScheduleFetchCurrenty() {
        logger.log("My Message2");    }

    @Scheduled(cron = "0 0/2 9-17 ? * SAT-SUN")
    public void ScheduleFetchCrypto() {
        logger.log("My Message3");    }
}
