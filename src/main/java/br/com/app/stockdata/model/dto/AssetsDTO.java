package br.com.app.stockdata.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AssetsDTO {
    public List<String> indexes;
    public List<String> stocks;
    public List<String> coins;
    public List<CurrencyDTO> currencies;
}
