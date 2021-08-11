package com.spring.giants.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;



@Getter
@Entity
public class Stock {

    @Id
    private int stockId;

    @NotNull
    private String stockName;

    @NotNull
    private String stockCode;

    @NotNull
    private String market;
}
