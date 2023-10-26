package br.com.app.stockdata.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Crypto {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String currency;
    private Double currencyRateFromUSD;
    private String coinName;
    private String coin;
    private Double regularMarketChange;
    private Double regularMarketPrice;
    private Double regularMarketChangePercent;
    private Double regularMarketDayLow;
    private Double regularMarketDayHigh;
    private String regularMarketDayRange;
    private Double regularMarketVolume;
    private Double marketCap;
    private Integer regularMarketTime;
    private String coinImageUrl;
}
