package com.spring.giants.service;

import com.spring.giants.model.dto.StockDto;
import com.spring.giants.model.entity.Stock;
import com.spring.giants.model.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;



@Service
@RequiredArgsConstructor
public class MainService {

    private final StockRepository stockRepository;


    public StockDto getStock(StockDto stockDto) {
        Stock stock = stockRepository.findByStockId(stockDto.getStockId());
        StockDto result = new StockDto(stock);
        return result;
    }

    public List<StockDto> getStockListByStockName(String stockName) {
        List<StockDto> stockDto = stockRepository.findStockListByStockName(stockName);

        if (stockDto == null) {
            throw new IllegalStateException("상장회사가 아닙니다.");
        }
        return stockDto;
    }

    public StockDto getStock(String stockName) {
        return stockRepository.findByStockName(stockName);
    }

    public StockDto getStockByStockName(String stockName) {
        return stockRepository.findByStockName(stockName);
    }

    public StockDto getStockByStockId(String stockId) {
        Stock stock = stockRepository.findByStockId(stockId);
        StockDto stockDto = new StockDto(stock);
        return stockDto;
    }
    public int getCountEqualStockName(String stockName) {
        return stockRepository.findCountByStockName(stockName);
    }
}
