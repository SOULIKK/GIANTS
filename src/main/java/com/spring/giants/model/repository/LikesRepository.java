package com.spring.giants.model.repository;

import com.spring.giants.model.entity.Board;
import com.spring.giants.model.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    void deleteByUserIdAndBoardId(Long userId, Long boardId);

    Likes findByUserIdAndBoardId(Long userId, Long boardId);

    void deleteByBoardId(Long boardId);

    List<Likes> findAllByBoardId(Long boardId);
}
