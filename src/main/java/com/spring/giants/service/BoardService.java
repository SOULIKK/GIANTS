package com.spring.giants.service;

import com.spring.giants.config.exception.ApiRequestException;
import com.spring.giants.model.dto.BoardDetailResponseDto;
import com.spring.giants.model.dto.BoardListResponseDto;
import com.spring.giants.model.dto.BoardRequestDto;
import com.spring.giants.model.entity.Board;
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
    public String setBoard(String username, BoardRequestDto boardRequestDto) {

        User user = userRepository.findByUsername(username);
        boardRequestDto.setUser(user);
        Board board = new Board(boardRequestDto);

        return boardRepository.save(board).getStockId();
    }


    @Transactional
    public List<BoardListResponseDto> getBoardList(String stockId) {
        return boardRepository.findAllByStockIdOrderByCreatedAtDesc(stockId);
    }
    @Transactional
    public BoardDetailResponseDto getDetail(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new ApiRequestException("해당 글이 존재하지 않습니다.")
        );

        BoardDetailResponseDto boardDetailResponseDto = new BoardDetailResponseDto(board);
        return boardDetailResponseDto;
    }
}
