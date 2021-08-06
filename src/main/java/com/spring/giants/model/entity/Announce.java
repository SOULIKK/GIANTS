package com.spring.giants.model.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
public class Announce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long announce_id;

    @NotNull
    private String stockId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stockId")
    private Stock stock;







}
