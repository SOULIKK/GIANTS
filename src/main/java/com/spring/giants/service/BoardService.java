package com.spring.giants.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.spring.giants.model.dto.*;
import com.spring.giants.model.entity.*;
import com.spring.giants.model.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {


    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final LikesRepository likesRepository;
    private final CommentRepository commentRepository;
    private final StockRepository stockRepository;


    @Transactional
    public void setBoard(String username, String stockId, BoardRequestDto boardRequestDto) {

        User user = userRepository.findByUsername(username);
        Stock stock = stockRepository.findByStockId(stockId);

        boardRequestDto.setUser(user);
        boardRequestDto.setStock(stock);
        Board board = new Board(boardRequestDto);

        boardRepository.save(board);
    }


    @Transactional
    public Page<BoardListResponseDto> getBoardList(StockDto stockDto, String search, Pageable pageable) {
        Stock stock = new Stock(stockDto);
        return boardRepository.findAllByStockAndTitleContainingOrderByCreatedAtDesc(stock, search, pageable);
    }


    @Transactional
    public BoardDetailResponseDto getDetail(Long boardId) {

        Board board = boardRepository.findById(boardId).orElseThrow(
            () -> new RuntimeException()
        );

        List<Comment> commentList = commentRepository.findAllByBoardOrderByCreatedAtDesc(board);
        List<CommentResponseDto> commentResponseDtoList = commentList.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());

        List<Likes> likesList = likesRepository.findAllByBoard(board);

        BoardDetailResponseDto boardDetailResponseDto = new BoardDetailResponseDto(board, commentResponseDtoList, likesList);
        return boardDetailResponseDto;
    }

    @Transactional
    public boolean setLike(String username, Long boardId) {

        User user = userRepository.findByUsername(username);
        Board board = boardRepository.findByBoardId(boardId);

        boolean isLiked = chkLike(username, boardId);

        if (!isLiked) {
            Likes likes = new Likes(user, board);
            likesRepository.save(likes);
            return true;
        } else {
            likesRepository.deleteByUserAndBoard(user, board);
            return false;
        }
    }

    @Transactional
    public boolean chkLike(String username, Long boardId) {
        User user = userRepository.findByUsername(username);
        Board board = boardRepository.findByBoardId(boardId);
        Likes isLiked = likesRepository.findByUserAndBoard(user, board);


        if (isLiked == null) {
            return false;
        } else {
            return true;
        }
    }

    @Transactional
    public void delBoard(Long boardId) {
        Board board = boardRepository.findByBoardId(boardId);
        boardRepository.deleteById(boardId);
        likesRepository.deleteByBoard(board);
    }

    @Transactional
    public List<Board> getUserBoard(User user) {
        return boardRepository.findByUser(user);
    }

    @Transactional
    public void uptBoard(Long boardId, BoardRequestDto boardRequestDto) {
        Board board = boardRepository.findOneByBoardId(boardId);
        board.update(boardRequestDto);
    }

    @Transactional
    public List<BoardListResponseDto> getMainBoardList(Stock stock) {

        return boardRepository.findTop10ByStockOrderByCreatedAtDesc(stock);

    }


    @Transactional
    public List<Board> getHotBoards() throws ParseException {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QBoard qBoard = QBoard.board;

        BooleanExpression expression = qBoard.likes.size().gt(1);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime beforeWeek = now.minusDays(7);

        BooleanExpression expression2 = qBoard.createdAt.after(beforeWeek);

        booleanBuilder.and(expression);
        booleanBuilder.and(expression2);


        List<Board> boards = (List<Board>) boardRepository.findAll(booleanBuilder);
        return boards;
    }


    public Page<BoardListResponseDto> getEdiorsPickBoards(String search, Pageable pageable) {
        return boardRepository.findAllByTitleContainingOrderByCreatedAtDesc(search, pageable);
    }

    public void setEpBoard(String username, BoardRequestDto boardRequestDto) {
        Board board = new Board(boardRequestDto);
        if (username.equals("admin")) {
            boardRepository.save(board);
        }

    }
}