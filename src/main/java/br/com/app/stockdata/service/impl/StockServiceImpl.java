package br.com.app.stockdata.service.impl;

import br.com.app.stockdata.model.Stock;
import br.com.app.stockdata.repository.StockRepository;
import br.com.app.stockdata.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    @Override
    public List<Stock> findDistinctSymbolsWithMaxCreateDate() {
        return stockRepository.findDistinctSymbolsWithMaxCreateDate();
    }

    @Override
    public List<Stock> findDistinctBySymbolWithMaxCreateDate(String symbol) {
        return stockRepository.findDistinctBySymbolWithMaxCreateDate(symbol);
    }
}
