package br.com.app.stockdata.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ExternalApiService {

    String fetchDataFromExternalApi( String ticket ) throws JsonProcessingException;

}
