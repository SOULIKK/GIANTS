package com.spring.giants.model.entity;


import com.spring.giants.model.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.activation.DataContentHandler;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @NotNull
    @Size(min = 10, max = 500, message = "10 ~ 500자 길이의 내용을 입력할 수 있습니다.")
    private String content;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ep_id")
    private EditorsPick editorsPick;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rcpNo")
    private Disclosure disclosure;

    public Comment(CommentRequestDto commentRequestDto, User user, Board board, EditorsPick editorsPick, Disclosure disclosure) {
        this.content = commentRequestDto.getContent();
        this.user = user;
        this.board = board;
        this.editorsPick = editorsPick;
        this.disclosure = disclosure;
    }


}
