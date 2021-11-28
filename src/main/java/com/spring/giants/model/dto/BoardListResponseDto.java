package com.spring.giants.model.dto;

import com.spring.giants.model.entity.Board;
import com.spring.giants.model.entity.Likes;
import com.spring.giants.model.entity.Stock;
import com.spring.giants.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BoardListResponseDto {

    private Long boardId;
    private String title;
    private String content;
    private User user;
    private Stock stock;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private int commentCount;
    private int likeCount;


    public BoardListResponseDto(Board board) {
        this.boardId = board.getBoardId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.user = board.getUser();
        this.stock = board.getStock();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }

}
