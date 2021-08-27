package com.spring.giants.model.dto;

import com.spring.giants.model.entity.Comment;
import com.spring.giants.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentResponseDto {

    private Long commentId;
    private String content;
    private User user;
    private LocalDateTime createdAt;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.user = comment.getUser();
        this.createdAt = comment.getCreatedAt();
    }

}