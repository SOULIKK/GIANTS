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
public class BoardDetailResponseDto {

    private Long boardId;
    private String title;
    private String content;
    private User user;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public BoardDetailResponseDto(Board board) {
        this.boardId = board.getBoardId();
        this.title = board.getTitle();
        this.content=  board.getContent();
        this.user = board.getUser();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }
}
