package br.com.app.stockdata.repository;

import br.com.app.stockdata.model.Crypto;
import br.com.app.stockdata.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CryptoRepository extends JpaRepository<Crypto, Integer> {

    Optional<Crypto> findTop1ByCoinAndUpdatedAtOrderByIdDesc(String coin, String updateAt);

    @Query(value = "SELECT DISTINCT ON (coin) *  FROM crypto ORDER BY coin, id DESC", nativeQuery = true)
    List<Crypto> findDistinctSymbolsWithMaxCreateDate();

    @Query(value = "SELECT DISTINCT ON (coin) * FROM crypto WHERE coin LIKE %:symbol% ORDER BY coin, id DESC", nativeQuery = true)
    List<Crypto> findDistinctBySymbolWithMaxCreateDate(@Param("symbol") String symbol);
}
