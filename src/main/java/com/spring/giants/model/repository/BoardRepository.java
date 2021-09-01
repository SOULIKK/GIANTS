package com.spring.giants.model.repository;

import com.spring.giants.model.dto.BoardListResponseDto;
import com.spring.giants.model.entity.Board;
import com.spring.giants.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<BoardListResponseDto> findAllByStockIdAndTitleContainingOrderByCreatedAtDesc(String stockId, String search, Pageable pageable);

    Board findByBoardId(Long boardId);

    List<Board> findByUser(User user);


    Board findOneByBoardId(Long boardId);
}
