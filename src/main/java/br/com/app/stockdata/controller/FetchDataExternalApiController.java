package br.com.app.stockdata.controller;

import br.com.app.stockdata.model.Stock;
import br.com.app.stockdata.service.ExternalApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
public class ExternalApiController {

    private final ExternalApiService stockService;

    @GetMapping("/stock")
    public String getStock() throws JsonProcessingException {
        return stockService.fetchDataFromExternalApi("PETR3");
    }
    @GetMapping("/currency")
    public String getCurrency() throws JsonProcessingException {
        return stockService.fetchDataFromExternalApi("PETR3");
    }
    @GetMapping("/crypto")
    public String getCrypto() throws JsonProcessingException {
        return stockService.fetchDataFromExternalApi("PETR3");
    }
}
