package com.spring.giants.model.entity;

import com.spring.giants.model.dto.BoardRequestDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Board extends Timestamped {

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


//    @ManyToOne
//    @JoinCol umn(name = "stockId")
    @NotNull
    private String stockId;



    public Board(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
        this.user = boardRequestDto.getUser();
        this.stockId = boardRequestDto.getStockId();
    }

}
