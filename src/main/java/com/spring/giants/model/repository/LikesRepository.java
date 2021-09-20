package com.spring.giants.model.repository;

import com.spring.giants.model.entity.Board;
import com.spring.giants.model.entity.Likes;
import com.spring.giants.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    void deleteByUserAndBoard(User user, Board board);

    Likes findByUserAndBoard(User user, Board board);

    void deleteByBoard(Board board);

    List<Likes> findAllByBoard(Board board);
}
