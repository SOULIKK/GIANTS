package com.spring.giants.model.entity;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @NotNull
    @Size(min = 2, max = 150, message = "제목은 2글자 이상이어야 합니다.")
    private String title;

    @NotNull
    private String content;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "stock_num")
    private Stock stockNum;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
