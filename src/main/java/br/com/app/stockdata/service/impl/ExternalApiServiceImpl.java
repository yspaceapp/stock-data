package br.com.app.stockdata.service.impl;

import br.com.app.stockdata.model.Stock;
import br.com.app.stockdata.model.Ticket;
import br.com.app.stockdata.model.Type;
import br.com.app.stockdata.model.dto.AssetsDTO;
import br.com.app.stockdata.repository.StockRepository;
import br.com.app.stockdata.repository.TicketRepository;
import br.com.app.stockdata.service.ExternalApiService;
import br.com.app.stockdata.util.ConvertUtils;
import br.com.app.stockdata.util.DateUltils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExternalApiServiceImpl implements ExternalApiService {

    @Value("${URL_STOCK}")
    private String urlStocks;

    @Value("${TOKEN_BR_API}")
    private String token;

    private final TicketRepository ticketRepository;
    private final StockRepository repository;
    private final ModelMapper mapper;
    private final RestTemplate restTemplate;

    public List<List<String>> groupList() {
        List<String> symbols = ticketRepository.findAll().stream()
                .map(Ticket::getSymbol)
                .collect(Collectors.toList());

        List<List<String>> groupedLists = new ArrayList<>();
        for (int i = 0; i < symbols.size(); i += 20) {
            int end = Math.min(i + 20, symbols.size());
            groupedLists.add(symbols.subList(i, end));
        }
        return groupedLists;
    }

    @Override
    public void fetchDataFromExternalApi() {
        AtomicInteger numberStockUpdated = new AtomicInteger();
        try {
            groupList().forEach(item -> {
                var url = trataUrl(urlStocks + String.join(",", item) + token);
                ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
                if (responseEntity.getStatusCode().is2xxSuccessful()) {
                    var assets = ConvertUtils.convertJsonToObject(responseEntity.getBody(), AssetsDTO.class);
                    assets.getResults().forEach(stock -> {
                        var lastStockSaved = repository.findTop1BySymbolAndUpdatedAtOrderByIdDesc(stock.getSymbol(), stock.getUpdatedAt());
                        if (lastStockSaved == null) {
                            stock.setType(stock.getShortName() == null || stock.getShortName().contains(Type.FII.name()) ? Type.FII.name() : Type.STOCK.name());
                            stock.setCreateAt(DateUltils.dateCurrent());
                            repository.save(mapper.map(stock, Stock.class));
                            numberStockUpdated.getAndIncrement();
                        }
                    });
                } else if (responseEntity.getStatusCode().is4xxClientError()) {
                    throw new HttpClientErrorException(responseEntity.getStatusCode(), "Error client ");
                } else if (responseEntity.getStatusCode().is5xxServerError()) {
                    throw new HttpClientErrorException(responseEntity.getStatusCode(), "Error Intern ");
                }
            });
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
        log.info("Updated Stocks " + numberStockUpdated + " - " + DateUltils.dateCurrent());
    }

    private String trataUrl(String url) {
        return url.replaceAll("\\[\\s]+|\\s+\\]", "").replaceAll(" ", "");
    }

}