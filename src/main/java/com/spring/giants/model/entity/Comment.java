package com.spring.giants.model.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @ManyToOne
    @JoinTable(name = "userId")
    private User user;

    @NotNull
    @ManyToOne
    @JoinTable(name = "boardId")
    private Board board;


}
