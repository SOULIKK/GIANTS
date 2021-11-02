package com.spring.giants.model.entity;


import lombok.Getter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
public class Stock {

    @Id
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
}
