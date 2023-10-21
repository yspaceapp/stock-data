package br.com.app.stockdata.service;

import br.com.app.stockdata.model.Stock;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface StockService {

    String fetchDataFromExternalApi() throws JsonProcessingException;

}
