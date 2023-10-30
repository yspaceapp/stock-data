package br.com.app.stockdata.service;

import br.com.app.stockdata.model.Assets;

import java.util.List;

public interface AssetsService {

    void updateListAssets();
    List<Assets> findAll();
}
