package com.spring.giants.model.repository;

import com.spring.giants.model.dto.BoardListResponseDto;
import com.spring.giants.model.dto.StockDto;
import com.spring.giants.model.entity.Board;
import com.spring.giants.model.entity.Stock;
import com.spring.giants.model.entity.User;
import com.spring.giants.model.repository.search.SearchBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, SearchBoardRepository, QuerydslPredicateExecutor<Board> {


    Board findByBoardId(Long boardId);

    List<Board> findByUser(User user);

    Board findOneByBoardId(Long boardId);

    List<BoardListResponseDto> findTop10ByStockOrderByCreatedAtDesc(Stock stock);

    Page<BoardListResponseDto> findAllByStockAndTitleContainingOrderByCreatedAtDesc(Stock stock, String search, Pageable pageable);

    Page<BoardListResponseDto> findAllByTitleContainingOrderByCreatedAtDesc(String search, Pageable pageable);

    @Query("SELECT b, u, COUNT(c), COUNT(l) " +
            "FROM Board b LEFT OUTER JOIN Comment c ON c.board = b " +
            "LEFT OUTER JOIN Likes l ON l.board = b " +
            "LEFT OUTER JOIN User u ON b.user = u " +
            "WHERE b.stock = :stock AND b.title LIKE %:search% ")
    Page<BoardListResponseDto> getBoardListWithCount(@Param("stock") Stock stock, @Param("search") String search, Pageable pageable);


}
