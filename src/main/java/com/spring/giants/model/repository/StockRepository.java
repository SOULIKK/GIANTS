package com.spring.giants.model.repository;

import com.spring.giants.model.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Integer> {

    Stock findByStockName(String stockName);

    Stock findByStockId(String stockId);
}
