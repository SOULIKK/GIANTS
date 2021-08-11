package com.spring.giants.model.dto;

import com.spring.giants.model.entity.Stock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StockResponseDto {

    private int stockId;
    private String stockName;
    private String stockCode;
    private String market;

    public StockResponseDto(Stock stock) {
        this.stockId = stock.getStockId();
        this.stockName = stock.getStockName();
        this.stockCode = stock.getStockCode();
        this.market = stock.getMarket();
    }
}
