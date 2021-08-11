package com.spring.giants.model.entity;

import com.spring.giants.model.dto.BoardRequestDto;
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
    @JoinColumn(name = "userId")
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "stockCode")
    private Stock stock;


    public Board(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
        this.user = boardRequestDto.getUser();
        this.stock = boardRequestDto.getStock();
    }
}
