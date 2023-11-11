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
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fromCurrency;
    private String toCurrency;
    private String name;
    private String high;
    private String low;
    private String bidVariation;
    private String percentageChange;
    private String bidPrice;
    private String askPrice;
    private String updatedAtTimestamp;
    private String updatedAtDate;
}
