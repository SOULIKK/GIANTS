package com.spring.giants.service;

import com.spring.giants.model.dto.BoardListResponseDto;
import com.spring.giants.model.dto.BoardRequestDto;
import com.spring.giants.model.entity.Board;
import com.spring.giants.model.entity.Stock;
import com.spring.giants.model.entity.User;
import com.spring.giants.model.repository.BoardRepository;
import com.spring.giants.model.repository.StockRepository;
import com.spring.giants.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {


    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final StockRepository stockRepository;

    @Transactional
    public void setBoard(String username, BoardRequestDto boardRequestDto) {

        User user = userRepository.findByUsername(username);
        boardRequestDto.setUser(user);
//        Stock stock = stockRepository.findByStockId(stockId);
//        boardRequestDto.setStock(stockId);
        Board board = new Board(boardRequestDto);

        boardRepository.save(board);
    }

//    @Transactional
//    public List<BoardListResponseDto> getBoardList(String stockId) {
//        Stock stock = stockRepository.findByStockId(stockId);
//        List<BoardListResponseDto> boardListResponseDto = (List<BoardListResponseDto>) boardRepository.findAllByStock(stock);
//
//        return boardListResponseDto;
//    }

}
