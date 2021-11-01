package com.spring.giants.model.repository;

import com.spring.giants.model.dto.BoardListResponseDto;
import com.spring.giants.model.dto.StockDto;
import com.spring.giants.model.entity.Board;
import com.spring.giants.model.entity.Stock;
import com.spring.giants.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, QuerydslPredicateExecutor<Board> {


    Board findByBoardId(Long boardId);

    List<Board> findByUser(User user);

    Board findOneByBoardId(Long boardId);

    List<BoardListResponseDto> findTop10ByStockOrderByCreatedAtDesc(String stockId);

    Page<BoardListResponseDto> findAllByStockAndTitleContainingOrderByCreatedAtDesc(String stockId, String search, Pageable pageable);
}
