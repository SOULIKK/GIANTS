package com.spring.giants.model.repository;

import com.spring.giants.model.dto.StockDto;
import com.spring.giants.model.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;


public interface StockRepository extends JpaRepository<Stock, Integer> {

    Stock findByStockId(String stockId);

    @Query("SELECT s FROM Stock s WHERE s.stockName =:stockName")
    StockDto findByStockName(@Param("stockName") String stockName);

    @Query("SELECT s FROM Stock s WHERE s.stockName LIKE %:stockName%")
    List<StockDto> findStockListByStockName(@Param("stockName") String stockName);

    @Query("SELECT count(s.stockId) FROM Stock s WHERE s.stockName =:stockName")
    int findCountByStockName(@Param("stockName") String stockName);
}
