package br.com.app.stockdata.service.impl;

import br.com.app.stockdata.model.Crypto;
import br.com.app.stockdata.repository.CryptoRepository;
import br.com.app.stockdata.service.CryptoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CryptoServiceImpl implements CryptoService {

    private final CryptoRepository cryptoRepository;

    @Override
    public List<Crypto> findDistinctSymbolsWithMaxCreateDate() {
        return cryptoRepository.findDistinctSymbolsWithMaxCreateDate();
    }

    @Override
    public List<Crypto> findDistinctBySymbolWithMaxCreateDate(String symbol) {
        return cryptoRepository.findDistinctBySymbolWithMaxCreateDate(symbol);
    }
}
