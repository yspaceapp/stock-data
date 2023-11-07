package br.com.app.stockdata.controller;

import br.com.app.stockdata.model.Stock;
import br.com.app.stockdata.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping
    public List<Stock> lastPriceStocks(){
        return stockService.findDistinctSymbolsWithMaxCreateDate();
    }

    @GetMapping("/{symbol}")
    public List<Stock> findDistinctBySymbolWithMaxCreateDate(@PathVariable("symbol") String symbol) {
        return stockService.findDistinctBySymbolWithMaxCreateDate(symbol);
    }

}
