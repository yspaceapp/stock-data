package br.com.app.stockdata.service.impl;

import br.com.app.stockdata.repository.TicketRepository;
import br.com.app.stockdata.util.ConvertUtils;
import br.com.app.stockdata.model.Stock;
import br.com.app.stockdata.model.dto.AssetsDTO;
import br.com.app.stockdata.repository.StockRepository;
import br.com.app.stockdata.service.ExternalApiService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ExternalApiServiceImpl implements ExternalApiService {

    @Value("${URL_STOCK}")
    String urlStocks;

    @Value("${TOKEN_BR_API}")
    String token;

    private final TicketRepository ticketRepository;

    private final StockRepository repository;

    private final ModelMapper mapper;

    private final RestTemplate restTemplate;

    public List<List<String>> groupList() {
        List<String> symbols = new ArrayList<>();
        ticketRepository.findAll().stream().forEach(item -> {
            symbols.add(item.getSymbol());
        });

        List<List<String>> groupedLists = new ArrayList<>();
        for (int i = 0; i < symbols.size(); i += 20) {
            int end = Math.min(i + 20, symbols.size());
            groupedLists.add(symbols.subList(i, end));
        }
        return groupedLists;
    }

    @Override
    public void fetchDataFromExternalApi() {
        try {
            groupList().stream().forEach(item -> {
                var url = urlStocks + item + token;
                url = url.replace("[", "").replace("]", "");
                url = url.replace(" ", "").replace(" ", "");
                ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
                if (responseEntity.getStatusCode().is2xxSuccessful()) {
                    var assets = ConvertUtils.convertJsonToObject(responseEntity.getBody(), AssetsDTO.class);
                    assets.getResults().forEach(stock -> {
                        repository.save(mapper.map(stock, Stock.class));
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

    }
}
