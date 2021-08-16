package com.spring.giants.model.dto;

import com.spring.giants.model.entity.Board;
import com.spring.giants.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BoardListResponseDto {
    private String title;
    private String content;
    private User user;
    private String stockId;



    public BoardListResponseDto(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.user = board.getUser();
        this.stockId = board.getStockId();
    }
}
