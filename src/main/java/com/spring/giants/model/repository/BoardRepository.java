package com.spring.giants.model.repository;

import com.spring.giants.model.dto.BoardListResponseDto;
import com.spring.giants.model.dto.StockMainBoardDto;
import com.spring.giants.model.entity.Board;
import com.spring.giants.model.entity.Stock;
import com.spring.giants.model.entity.User;
import com.spring.giants.model.repository.searchPageBoard.SearchPageBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BoardRepository extends JpaRepository<Board, Long>, SearchPageBoardRepository, QuerydslPredicateExecutor<Board> {


    Board findByBoardId(Long boardId);

    Board findOneByBoardId(Long boardId);

    @Query("SELECT new com.spring.giants.model.dto.StockMainBoardDto(b.boardId, b.title, b.user.nickname, b.createdAt) " +
            " FROM Board b LEFT OUTER JOIN User u ON b.user = u " +
            " WHERE b.stock.stockId =:stockId ORDER BY b.createdAt ")
    List<StockMainBoardDto> findTop10ByStockOrderByCreatedAtDesc(@Param("stockId") String stockId);

}
