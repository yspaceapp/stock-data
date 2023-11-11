package br.com.app.stockdata.controller;

import br.com.app.stockdata.model.Crypto;
import br.com.app.stockdata.service.CryptoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/crypto")
@RequiredArgsConstructor
public class CryptoController {

    private final CryptoService cryptoService;

    @GetMapping
    public List<Crypto> lastPriceStocks(){
        return cryptoService.findDistinctSymbolsWithMaxCreateDate();
    }

    @GetMapping("/{symbol}")
    public List<Crypto> findDistinctBySymbolWithMaxCreateDate(@PathVariable("symbol") String symbol) {
        return cryptoService.findDistinctBySymbolWithMaxCreateDate(symbol);
    }

}
