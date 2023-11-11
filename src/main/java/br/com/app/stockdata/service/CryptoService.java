package br.com.app.stockdata.service;

import br.com.app.stockdata.model.Crypto;
import br.com.app.stockdata.model.Stock;

import java.util.List;

public interface CryptoService {

    List<Crypto> findDistinctSymbolsWithMaxCreateDate();

    List<Crypto> findDistinctBySymbolWithMaxCreateDate(String symbol);
}