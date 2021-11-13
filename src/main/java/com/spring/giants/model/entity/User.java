package com.spring.giants.model.entity;



import com.spring.giants.model.dto.ProfileRequestDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    private String username;

    @NotNull
    private String password;

    private Boolean enabled;

//    @ManyToMany
//    @JoinTable(
//            name = "user_role"
//            , joinColumns = @JoinColumn(name = "user_id")
//            , inverseJoinColumns = @JoinColumn(name = "role_id")
//    )
//    private List<Role> roles = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    private UserRole role;


    @OneToMany(mappedBy = "user")
    private List<Board> boards = new ArrayList<>();

    public void update(ProfileRequestDto profileRequestDto) {
        this.password = profileRequestDto.getPassword();
    }


    public User(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

}
