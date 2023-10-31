package br.com.app.stockdata.job;

import br.com.app.stockdata.service.ExternalApiService;
import br.com.app.stockdata.util.DateUltils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class ScheduledTaskUpdateFetchData {

    private final ExternalApiService externalApiService;

    @Scheduled(cron = "0 0/3 9-16 ? * MON-FRI")
    public void ScheduleFetchStock() {
        externalApiService.fetchDataFromExternalApi();
    }

}
