package br.com.app.stockdata.service.impl;

import br.com.app.stockdata.model.Crypto;
import br.com.app.stockdata.model.Stock;
import br.com.app.stockdata.model.Type;
import br.com.app.stockdata.model.dto.AssetsDTO;
import br.com.app.stockdata.model.dto.CryptosDTO;
import br.com.app.stockdata.model.dto.StocksDTO;
import br.com.app.stockdata.repository.CryptoRepository;
import br.com.app.stockdata.repository.StockRepository;
import br.com.app.stockdata.service.AssetsService;
import br.com.app.stockdata.service.ExternalApiService;
import br.com.app.stockdata.util.ConvertUtils;
import br.com.app.stockdata.util.DateUltils;
import br.com.app.stockdata.util.UrlUtils;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class ExternalApiServiceImpl implements ExternalApiService {

    @Value("${URL_STOCK}")
    private String urlStocks;

    @Value("${URL_CRYPTO}")
    private String urlCryptos;

    @Value("${TOKEN_BR_API}")
    private String token;

    private final AssetsService assetsService;
    private final StockRepository stockRepository;
    private final CryptoRepository cryptoRepository;
    private final ModelMapper mapper;
    private final RestTemplate restTemplate;

    public List<List<AssetsDTO>> groupListDto() {
        var assetsDTOStream = assetsService.findAll().stream().map(item -> new AssetsDTO(item.getType(), item.getSymbol())).toList();

        List<List<AssetsDTO>> groupedLists = new ArrayList<>();
        for (int i = 0; i < assetsDTOStream.size(); i += 20) {
            int end = Math.min(i + 20, assetsDTOStream.size());
            groupedLists.add(assetsDTOStream.subList(i, end));
        }
        return groupedLists;
    }

    @Override
    public void fetchDataFromExternalApi() {
        groupListDto().forEach(item -> item.forEach(it -> {
            String url = getUrl(it);
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
            Type type = Type.valueOf(it.getType());

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                saveAssets(responseEntity.getBody(), type);
            }
            else if (responseEntity.getStatusCode().is4xxClientError()) {
                throw new HttpClientErrorException(responseEntity.getStatusCode(), "Client error");
            } else if (responseEntity.getStatusCode().is5xxServerError()) {
                throw new HttpClientErrorException(responseEntity.getStatusCode(), "Internal error");
            }
        }));
    }

    private void saveAssets(String response, Type type) {
        switch (type) {
            case STOCK -> saveStocks(ConvertUtils.convertJsonToObject(response, StocksDTO.class));
            case CRYPTO -> saveCryptos(ConvertUtils.convertJsonToObject(response, CryptosDTO.class));
        }
    }

    private void saveStocks(StocksDTO stocksDTO) {
        stocksDTO.getResults().forEach(stock -> {
            if (stockRepository.findTop1BySymbolAndUpdatedAtOrderByIdDesc(stock.getSymbol(), stock.getUpdatedAt()).isEmpty()) {
                stock.setType(stock.getShortName() == null || stock.getShortName().contains(Type.FII.name()) ? Type.FII.name() : Type.STOCK.name());
                stock.setCreateAt(DateUltils.dateCurrent());
                stockRepository.save(mapper.map(stock, Stock.class));
                log.info("Stock: " + stock.getSymbol() + " price: " + stock.getRegularMarketPrice());
            }
        });
    }

    private void saveCryptos(CryptosDTO cryptosDTO) {
        cryptosDTO.getCoins().forEach(crypto -> {
            if (cryptoRepository.findTop1ByCoinAndUpdatedAtOrderByIdDesc(crypto.getCoin(), crypto.getUpdatedAt()).isEmpty()) {
                crypto.setType(Type.CRYPTO.name());
                crypto.setCreateAt(DateUltils.dateCurrent());
                cryptoRepository.save(mapper.map(crypto, Crypto.class));
                log.info("Coin: " + crypto.getCoin() + " price: " + crypto.getRegularMarketPrice());
            }
        });
    }

    private String getUrl(AssetsDTO asset) {
        String url = "";
        if (Type.STOCK.name().equalsIgnoreCase(asset.getType())) {
            url = urlStocks;
        } else if (Type.CRYPTO.name().equalsIgnoreCase(asset.getType())) {
            url = urlCryptos;
        }
        return UrlUtils.tratarUrl(url + String.join(",", asset.getSympol()) + token);
    }

}