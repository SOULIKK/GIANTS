package com.spring.giants.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;


    @JsonIgnore
    @OneToMany(mappedBy = "board")
    private List<Board> boards = new ArrayList<>();

    @NotNull
    private String name;
}
