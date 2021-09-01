package com.spring.giants.model.entity;

import com.spring.giants.model.dto.BoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @NotNull
//    @Size(min = 2, max = 150, message = "2 ~ 150자 길이의 제목을 입력할 수 있습니다.")
    private String title;

    @NotNull
//    @Size(min = 10, max = 2000, message = "10 ~ 2000자 길이의 내용을 입력할 수 있습니다.")
    private String content;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;


    @NotNull
    private String stockId;

    public Board(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
        this.user = boardRequestDto.getUser();
        this.stockId = boardRequestDto.getStockId();
    }

    public void update(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
    };


}
