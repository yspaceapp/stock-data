package br.com.app.stockdata.model.dto;

import br.com.app.stockdata.model.Crypto;
import br.com.app.stockdata.model.Stock;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CryptosDTO {
    private List<Crypto> coins;
}