package com.spring.giants.model.entity;


import lombok.Data;
import lombok.Getter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
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
}
