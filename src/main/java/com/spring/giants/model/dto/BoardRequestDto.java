package com.spring.giants.model.dto;

import com.spring.giants.model.entity.User;
import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BoardRequestDto {

    private String title;
    private String content;
    private User user;

}
