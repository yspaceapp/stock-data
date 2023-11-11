package br.com.app.stockdata.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResultDTO {
    private List<String> indexes;
    private List<String> stocks;
}
