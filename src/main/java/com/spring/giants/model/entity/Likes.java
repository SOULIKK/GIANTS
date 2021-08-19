package com.spring.giants.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@RequiredArgsConstructor
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long likeId;

    private Long userId;

    private Long boardId;

    public Likes(Long userId, Long boardId) {
        this.userId = userId;
        this.boardId = boardId;
    }

}
