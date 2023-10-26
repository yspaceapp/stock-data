package br.com.app.stockdata.job;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@AllArgsConstructor
@Slf4j
public class ScheduledTaskStock {

    @Scheduled(cron = "0 0/2 9-17 ? * SAT-SUN")
    public void ScheduleFetchStock() {
        log.info("My Message1");    }

    @Scheduled(cron = "0 0/2 9-17 ? * SAT-SUN")
    public void ScheduleFetchCurrenty() {
        log.info("My Message1");    }

    @Scheduled(cron = "0 0/2 9-17 ? * SAT-SUN")
    public void ScheduleFetchCrypto() {
        log.info("My Message1");    }
}
