package br.com.app.stockdata.service;

import br.com.app.stockdata.model.Stock;

import java.util.List;

public interface StockService {

    List<Stock> findDistinctSymbolsWithMaxCreateDate();

    List<Stock> findDistinctBySymbolWithMaxCreateDate(String symbol);
}