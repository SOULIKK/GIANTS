package com.spring.giants.model.dto;

import com.spring.giants.model.entity.Board;
import com.spring.giants.model.entity.Stock;
import com.spring.giants.model.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HotBoardResponseDto {

    private Long boardId;
    private String title;
    private String content;
    private User user;
    private Stock stock;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long commentCnt;
    private Long likesCnt;

    public HotBoardResponseDto(Board board, Long commentCnt, Long likesCnt) {
        this.boardId = board.getBoardId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.user = board.getUser();
        this.stock = board.getStock();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.commentCnt = commentCnt;
        this.likesCnt = likesCnt;

    }
}
