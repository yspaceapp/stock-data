package br.com.app.stockdata.model.dto;

import br.com.app.stockdata.model.Stock;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class AssetsDTO {
    private List<Stock> results;
    private Date requestedAt;
    private String took;
}
