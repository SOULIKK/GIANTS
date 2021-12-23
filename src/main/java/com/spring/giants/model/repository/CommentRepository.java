package com.spring.giants.model.repository;

import com.spring.giants.model.dto.CommentResponseDto;
import com.spring.giants.model.entity.Board;
import com.spring.giants.model.entity.Comment;
import com.spring.giants.model.entity.EditorsPick;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByBoardOrderByCreatedAtDesc(Board board);

    void deleteByBoard(Board board);


    List<CommentResponseDto> findAllByEditorsPickOrderByCreatedAtDesc(EditorsPick editorsPick);
}
