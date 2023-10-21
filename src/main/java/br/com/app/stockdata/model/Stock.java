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
public class Stock {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String symbol;
    private String currency;
    private Double twoHundredDayAverage;
    private Double twoHundredDayAverageChange;
    private Double twoHundredDayAverageChangePercent;
    private Long marketCap;
    private String shortName;
    private String longName;
    private Double regularMarketChange;
    private Double regularMarketChangePercent;
    private String regularMarketTime;
    private Double regularMarketPrice;
    private Double regularMarketDayHigh;
    private String regularMarketDayRange;
    private Double regularMarketDayLow;
    private Integer regularMarketVolume;
    private Double regularMarketPreviousClose;
    private Double regularMarketOpen;
    private Integer averageDailyVolume3Month;
    private Integer averageDailyVolume10Day;
    private Double fiftyTwoWeekLowChange;
    private Double fiftyTwoWeekLowChangePercent;
    private String fiftyTwoWeekRange;
    private Double fiftyTwoWeekHighChange;
    private Double fiftyTwoWeekHighChangePercent;
    private Double fiftyTwoWeekLow;
    private Double fiftyTwoWeekHigh;
    private Double priceEarnings;
    private Double earningsPerShare;
    private String logourl;
    private String updatedAt;
}
