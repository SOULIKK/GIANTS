package com.spring.giants.model.repository;


import com.spring.giants.model.entity.TempUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TempUserRepository extends JpaRepository<TempUser, String> {

    TempUser findByEmail(String email);
}
