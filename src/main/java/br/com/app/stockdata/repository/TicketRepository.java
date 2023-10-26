package br.com.app.stockdata.repository;

import br.com.app.stockdata.model.Stock;
import br.com.app.stockdata.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Integer> {


}
