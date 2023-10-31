package br.com.app.stockdata.service.impl;

import br.com.app.stockdata.constants.ServiceConstants;
import br.com.app.stockdata.model.Assets;
import br.com.app.stockdata.model.Type;
import br.com.app.stockdata.model.dto.AssetsDTO;
import br.com.app.stockdata.model.dto.CurrencyDto;
import br.com.app.stockdata.repository.AssetsRepository;
import br.com.app.stockdata.service.AssetsService;
import br.com.app.stockdata.util.ConvertUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetsServiceImpl implements AssetsService {

    @Value("${URL_STOCK_AVALIABLE}")
    private String urlStocksAvailable;

    @Value("${URL_CRYPTO_AVALIABLE}")
    private String urlCryptoAvailable;

    @Value("${URL_CURRENCY_AVALIABLE}")
    private String urlCurrencyAvailable;

    private final RestTemplate restTemplate;
    private final AssetsRepository assetsRepository;

    @Override
    public void updateListAssets(String type) {
        String url = determineURLByType(type);
        updateAssets(url, type);
    }

    private String determineURLByType(String type) {
        switch (type) {
            case ServiceConstants.CRYPTO:
                return urlCryptoAvailable;
            case ServiceConstants.CURRENCY:
                return urlCurrencyAvailable;
            default:
                return urlStocksAvailable;
        }
    }

    private void updateAssets(String url, String type) {
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                var assets = ConvertUtils.convertJsonToObject(responseEntity.getBody(), AssetsDTO.class);
                saveAssetsBasedOnType(assets, type);
            } else {
                handleErrorResponse((HttpStatus) responseEntity.getStatusCode());
            }
        } catch (Exception e) {
            handleException(e);
        }
    }

    private void saveAssetsBasedOnType(AssetsDTO assets, String type) {
        switch (type) {
            case ServiceConstants.STOCK:
                saveFilteredAssets(assets.getStocks(),Type.STOCK);
                break;
            case ServiceConstants.CRYPTO:
                saveFilteredAssets(assets.getCoins(),Type.CRYPTO);
                break;
            case ServiceConstants.CURRENCY:
                saveFilteredAssetsCurrency(assets.getCurrencies());
                break;
            default:
                throw new IllegalArgumentException("Invalid asset type: " + type);
        }
    }

    private void saveFilteredAssetsCurrency(List<CurrencyDto> currencies) {
        currencies.forEach(currency ->
                assetsRepository.save(Assets.builder()
                        .symbol(currency.getCurrency())
                        .name(currency.getName())
                        .type(Type.CURRENCY.name())
                        .build())
        );
    }

    private void saveFilteredAssets(List<String> symbols, Type type) {
        symbols.stream()
                .filter(symbol -> !symbol.endsWith("F"))
                .forEach(symbol -> {
                    assetsRepository.save(Assets.builder().name(symbol).symbol(symbol).type(type.name()).build());
                } );
    }

    private void handleErrorResponse(HttpStatus statusCode) {
        if (statusCode.is4xxClientError()) {
            throw new HttpClientErrorException(statusCode, "Client error");
        } else if (statusCode.is5xxServerError()) {
            throw new HttpClientErrorException(statusCode, "Internal server error");
        }
    }

    private void handleException(Exception e) {
        if (e instanceof HttpClientErrorException) {
            HttpClientErrorException exception = (HttpClientErrorException) e;
            throw new HttpClientErrorException(exception.getStatusCode(), exception.getMessage());
        } else {
            throw new RuntimeException("An unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public List<Assets> findAll() {
        return assetsRepository.findAll();
    }
}
