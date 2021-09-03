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

    private String stockId;
    private String stockName;
    private String market;
    private String sector;
    private String product;

    public StockResponseDto(Stock stock) {
        this.stockId = stock.getStockId();
        this.stockName = stock.getStockName();
        this.market = stock.getMarket();
        this.sector = stock.getSector();
        this.product = stock.getProduct();
    }
}
