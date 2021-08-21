package com.spring.giants.model.dto;

import com.spring.giants.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentResponseDto {

    private Long commentId;
    private String content;
    private User user;

}
