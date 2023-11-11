package br.com.app.stockdata.repository;

import br.com.app.stockdata.model.Assets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetsRepository extends JpaRepository<Assets, Integer> {

}
