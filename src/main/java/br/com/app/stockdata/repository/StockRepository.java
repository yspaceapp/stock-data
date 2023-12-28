package br.com.app.stockdata.repository;

import br.com.app.stockdata.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Integer> {

    Optional<Stock> findTop1BySymbolAndUpdatedAtOrderByIdDesc(String symbol, String updateAt);

    @Query(value = "SELECT DISTINCT ON (symbol) *  FROM stock ORDER BY symbol, create_at DESC", nativeQuery = true)
    List<Stock> findDistinctSymbolsWithMaxCreateDate();

    @Query(value = "SELECT DISTINCT ON (symbol) * FROM stock WHERE symbol LIKE %:symbol% ORDER BY symbol, create_at DESC", nativeQuery = true)
    List<Stock> findDistinctBySymbolWithMaxCreateDate(@Param("symbol") String symbol);
}
