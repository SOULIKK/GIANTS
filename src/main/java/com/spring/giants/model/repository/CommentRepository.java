package com.spring.giants.model.repository;

import com.spring.giants.model.entity.Board;
import com.spring.giants.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByBoardOrderByCreatedAtDesc(Board board);
}
