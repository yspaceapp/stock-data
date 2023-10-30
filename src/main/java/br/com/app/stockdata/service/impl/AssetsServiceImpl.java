package br.com.app.stockdata.service.impl;

import br.com.app.stockdata.model.Assets;
import br.com.app.stockdata.model.dto.AssetsDTO;
import br.com.app.stockdata.repository.AssetsRepository;
import br.com.app.stockdata.service.AssetsService;
import br.com.app.stockdata.util.ConvertUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetsServiceImpl implements AssetsService {


    @Value("${URL_STOCK_AVALIABLE}")
    private String urlStocksAvaliable;

    private final RestTemplate restTemplate;

    private final AssetsRepository assetsRepository;

    @Override
    public void updateListAssets() {
        try {
                ResponseEntity<String> responseEntity = restTemplate.getForEntity(urlStocksAvaliable, String.class);
                if (responseEntity.getStatusCode().is2xxSuccessful()) {
                    var assets = ConvertUtils.convertJsonToObject(responseEntity.getBody(), AssetsDTO.class);
                    assets.getStocks().forEach(symbol -> {
                        if (!symbol.endsWith("F")){
                            assetsRepository.save(Assets.builder().symbol(symbol).build());
                        }
                    });
                } else if (responseEntity.getStatusCode().is4xxClientError()) {
                    throw new HttpClientErrorException(responseEntity.getStatusCode(), "Error client ");
                } else if (responseEntity.getStatusCode().is5xxServerError()) {
                    throw new HttpClientErrorException(responseEntity.getStatusCode(), "Error Intern ");
                }
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), e.getMessage());
        }
    }

    @Override
    public List<Assets> findAll() {
        return assetsRepository.findAll();
    }
}
