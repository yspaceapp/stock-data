package br.com.app.stockdata.service;


public interface ExternalApiService {

    void fetchDataFromExternalApi();

    void fetchDataFromExternalApi(String type);

}
