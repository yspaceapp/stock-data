package br.com.app.stockdata.service.impl;

import br.com.app.stockdata.repository.TicketRepository;
import br.com.app.stockdata.service.TicketService;
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
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
public class ExternalApiServiceImpl implements ExternalApiService {

    @Value("${URL_STOCK}")
    String urlStock;

    @Value("${TOKEN_BR_API}")
    String token;

    private final StockRepository repository;
    private final TicketRepository ticketRepository;
    private final ModelMapper mapper;

    private final RestTemplate restTemplate;

    public String fetchDataFromExternalApi(String ticket) {

        try {
            String apiUrl = urlStock + ticket + token;
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                var assets = ConvertUtils.convertJsonToObject(responseEntity.getBody(), AssetsDTO.class);
                assets.getResults().forEach(item -> {
                    repository.save(mapper.map(item, Stock.class));
                });
                return "OK";
            } else if (responseEntity.getStatusCode().is4xxClientError()) {
                throw new HttpClientErrorException(responseEntity.getStatusCode(), "Error client ");
            } else if (responseEntity.getStatusCode().is5xxServerError()) {
                throw new HttpClientErrorException(responseEntity.getStatusCode(), "Error Intern ");
            }
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
        return "ERROR";
    }
}
