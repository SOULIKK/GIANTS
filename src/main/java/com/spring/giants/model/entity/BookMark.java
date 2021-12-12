package com.spring.giants.model.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;



@Entity
@Getter
@RequiredArgsConstructor
public class BookMark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookMarkId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "epId")
    private EditorsPick editorsPick;

    public BookMark(User user, EditorsPick editorsPick) {
        this.user = user;
        this.editorsPick = editorsPick;
    }


}
