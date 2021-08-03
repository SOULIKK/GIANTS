package com.spring.giants.model.entity;



import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private int hit;

    @NotNull
    private int like;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
