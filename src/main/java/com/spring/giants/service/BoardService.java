package com.spring.giants.service;

import com.spring.giants.config.exception.ApiRequestException;
import com.spring.giants.model.dto.BoardDetailResponseDto;
import com.spring.giants.model.dto.BoardListResponseDto;
import com.spring.giants.model.dto.BoardRequestDto;
import com.spring.giants.model.entity.Board;
import com.spring.giants.model.entity.Likes;
import com.spring.giants.model.entity.User;
import com.spring.giants.model.repository.BoardRepository;
import com.spring.giants.model.repository.LikesRepository;
import com.spring.giants.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {


    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final LikesRepository likesRepository;


    @Transactional
    public String setBoard(String username, BoardRequestDto boardRequestDto) {

        User user = userRepository.findByUsername(username);
        boardRequestDto.setUser(user);
        Board board = new Board(boardRequestDto);

        return boardRepository.save(board).getStockId();
    }


    @Transactional
    public Page<BoardListResponseDto> getBoardList(String stockId, String search, Pageable pageable) {
        
        return boardRepository.findAllByStockIdAndTitleContainingOrderByCreatedAtDesc(stockId, search, pageable);
    }


    @Transactional
    public BoardDetailResponseDto getDetail(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new ApiRequestException("해당 글이 존재하지 않습니다.")
        );

        BoardDetailResponseDto boardDetailResponseDto = new BoardDetailResponseDto(board);
        return boardDetailResponseDto;
    }

    @Transactional
    public boolean setLike(String username, Long boardId) {

        User user = userRepository.findByUsername(username);
        Long userId = user.getUserId();

        boolean isLiked = chkLike(username, boardId);

        if (!isLiked) {
            Likes likes = new Likes(userId, boardId);
            likesRepository.save(likes);
            return true;
        } else {
            likesRepository.deleteByUserIdAndBoardId(userId, boardId);
            return false;
        }
    }

    @Transactional
    public boolean chkLike(String username, Long boardId) {
        User user = userRepository.findByUsername(username);
        Likes isLiked = likesRepository.findByUserIdAndBoardId(user.getUserId(), boardId);

        if (isLiked == null) {
            return false;
        } else {
            return true;
        }
    }

    @Transactional
    public void delBoard(Long boardId) {
        boardRepository.deleteById(boardId);
        likesRepository.deleteByBoardId(boardId);
    }
}
