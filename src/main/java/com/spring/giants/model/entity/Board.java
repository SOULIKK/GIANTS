package com.spring.giants.model.entity;

import com.spring.giants.model.dto.BoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @NotNull
    @Size(min = 2, max = 150, message = "2 ~ 150자 길이의 제목을 입력할 수 있습니다.")
    private String title;

    @NotNull
    @Size(min = 10, max = 2000, message = "10 ~ 2000자 길이의 내용을 입력할 수 있습니다.")
    private String content;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "board")
    private List<Likes> likes = new ArrayList<>();


//    @NotNull
//    private String stockId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stockId")
    private Stock stock;

    public Board(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
        this.user = boardRequestDto.getUser();
//        this.stockId = boardRequestDto.getStockId();
        this.stock = boardRequestDto.getStock();
    }

    public void update(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
    };


}
