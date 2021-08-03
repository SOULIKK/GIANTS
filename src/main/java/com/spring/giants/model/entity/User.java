package com.spring.giants.model.entity;



import lombok.Data;

;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private int state;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();




}
