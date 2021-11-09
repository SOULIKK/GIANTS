package com.spring.giants.model.entity;


import com.spring.giants.model.dto.StockDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String stockId;

    @NotNull
    private String stockName;

    @NotNull
    private String market;

    private String sector;

    private String product;

    private String homepage;

    @OneToMany(mappedBy = "stock")
    private List<Board> boards = new ArrayList<>();

    public Stock(StockDto stockDto) {
        this.stockId = stockDto.getStockId();
        this.stockName = stockDto.getStockName();
        this.market = stockDto.getMarket();
        this.sector = stockDto.getSector();
        this.product = stockDto.getProduct();
        this.homepage = stockDto.getHomepage();
    }

}
