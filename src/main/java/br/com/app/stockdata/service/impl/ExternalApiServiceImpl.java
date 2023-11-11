package br.com.app.stockdata.service.impl;

import br.com.app.stockdata.model.Assets;
import br.com.app.stockdata.model.Stock;
import br.com.app.stockdata.model.Type;
import br.com.app.stockdata.model.dto.StocksDTO;
import br.com.app.stockdata.repository.StockRepository;
import br.com.app.stockdata.service.AssetsService;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class ExternalApiServiceImpl implements ExternalApiService {

    @Value("${URL_STOCK}")
    private String urlStocks;

    @Value("${TOKEN_BR_API}")
    private String token;

    private final AssetsService assetsService;
    private final StockRepository stockRepository;
    private final ModelMapper mapper;
    private final RestTemplate restTemplate;

    public List<List<String>> groupList() {
        List<String> symbols = assetsService.findAll().stream().filter(item -> item.getType().equals("STOCK"))
                .map(Assets::getSymbol).toList();

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
                var url = trataUrl(urlStocks + String.join(",", item) + token); //muda
                ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
                if (responseEntity.getStatusCode().is2xxSuccessful()) {
                    var assets = ConvertUtils.convertJsonToObject(responseEntity.getBody(), StocksDTO.class);
                    assets.getResults().forEach(stock -> {
                        var lastStockSaved = stockRepository.findTop1BySymbolAndUpdatedAtOrderByIdDesc(stock.getSymbol(), stock.getUpdatedAt());
                        if (lastStockSaved == null) {
                            stock.setType(stock.getShortName() == null || stock.getShortName().contains(Type.FII.name()) ? Type.FII.name() : Type.STOCK.name());
                            stock.setCreateAt(DateUltils.dateCurrent());
                            stockRepository.save(mapper.map(stock, Stock.class));
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


    public List<List<String>> groupList(String type) {
        List<String> symbols = assetsService.findAll().stream().filter(item -> item.getType().equals(type))
                .map(Assets::getSymbol).toList();

        List<List<String>> groupedLists = new ArrayList<>();
        for (int i = 0; i < symbols.size(); i += 20) {
            int end = Math.min(i + 20, symbols.size());
            groupedLists.add(symbols.subList(i, end));
        }
        return groupedLists;
    }

    @Override
    public void fetchDataFromExternalApi(String type) {
        AtomicInteger numberStockUpdated = new AtomicInteger();
        try {
            groupList().forEach(item -> {
                var url = trataUrl(urlStocks + String.join(",", item) + token); //muda
                ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
                if (responseEntity.getStatusCode().is2xxSuccessful()) {
                    var assets = ConvertUtils.convertJsonToObject(responseEntity.getBody(), StocksDTO.class);
                    assets.getResults().forEach(asset -> {
                        System.out.println(asset);

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

    private Object defineObjectByType(String type){

        if(type.equals(Type.STOCK)){
            System.out.println("TESTE");
        }
        return null;

    }


    private String trataUrl(String url) {
        return url.replaceAll("\\[\\s]+|\\s+\\]", "").replaceAll(" ", "");
    }

}