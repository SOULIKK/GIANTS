package com.spring.giants.model.entity;


import com.spring.giants.model.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.activation.DataContentHandler;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @NotNull
    private String content;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public Comment(CommentRequestDto commentRequestDto, User user, Board board) {
        this.content = commentRequestDto.getContent();
        this.user = user;
        this.board = board;
    }


}
