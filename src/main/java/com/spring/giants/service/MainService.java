package com.spring.giants.service;

import com.spring.giants.model.dto.StockDto;
import com.spring.giants.model.entity.Stock;
import com.spring.giants.model.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MainService {

    private final StockRepository stockRepository;

    @Transactional
    public StockDto getStock(StockDto stockDto) {
        Stock stock = stockRepository.findByStockId(stockDto.getStockId());
        StockDto result = new StockDto(stock);
        return result;
    }

    @Transactional
    public StockDto stockSearch(String stockName) {
        Stock stock = stockRepository.findByStockName(stockName);
        StockDto stockDto = new StockDto(stock);
        return stockDto;
    }
}
