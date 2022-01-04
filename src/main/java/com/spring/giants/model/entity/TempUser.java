package com.spring.giants.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@Data
@NoArgsConstructor
public class TempUser {

    @Id
    private String email;
    private String certKey;
}
