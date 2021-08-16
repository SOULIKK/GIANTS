package com.spring.giants.service;

import com.spring.giants.model.dto.StockResponseDto;
import com.spring.giants.model.entity.Stock;
import com.spring.giants.model.repository.BoardRepository;
import com.spring.giants.model.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MainService {

    private final BoardRepository boardRepository;
    private final StockRepository stockRepository;

    @Transactional
    public StockResponseDto getStockId(String stockName) {
        Stock stock = stockRepository.findByStockName(stockName);
        StockResponseDto stockResponseDto = new StockResponseDto(stock);

        return stockResponseDto;
    }
}