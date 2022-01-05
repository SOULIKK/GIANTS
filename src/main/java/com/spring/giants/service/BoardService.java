package com.spring.giants.service;

import com.spring.giants.model.dto.*;
import com.spring.giants.model.entity.*;
import com.spring.giants.model.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final LikesRepository likesRepository;
    private final CommentRepository commentRepository;
    private final StockRepository stockRepository;

    private final MainService mainService;


    @Transactional
    public void setBoard(String username, String stockId, BoardRequestDto boardRequestDto) {

        User user = userRepository.findByUsername(username);
        Stock stock = stockRepository.findByStockId(stockId);
        boardRequestDto.setUser(user);
        boardRequestDto.setStock(stock);
        Board board = new Board(boardRequestDto);
        boardRepository.save(board);
    }


    public PageResultDto<BoardListResponseDto, Object[]> getList(String boardType, String username, String stockId, PageRequestDto pageRequestDto) {

        User user = userRepository.findByUsername(username);
        Function<Object[], BoardListResponseDto> fn = (en -> entityToDto((Board)en[0], (User)en[1], (Long)en[2], (Long)en[3]));

        Page<Object[]> result = boardRepository.searchPageStockBoard(
                boardType,
                user,
                stockId,
                pageRequestDto.getType(),
                pageRequestDto.getKeyword(),
                pageRequestDto.getPageable(Sort.by("boardId").descending())
        );

        return new PageResultDto<>(result, fn);
    }

    BoardListResponseDto entityToDto(Board board, User user, Long commentCount, Long likeCount) {
        BoardListResponseDto boardListResponseDto = BoardListResponseDto.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .content(board.getContent())
                .user(user)
                .stock(mainService.getStockByBoardId(board.getBoardId()))
                .createdAt(board.getCreatedAt())
                .commentCount(commentCount.intValue())
                .likeCount(likeCount.intValue())
                .build();
        return boardListResponseDto;
    }

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

        boolean isLiked = chkLike(user, board);

        if (!isLiked) {
            Likes likes = new Likes(user, board);
            likesRepository.save(likes);
            return true;
        } else {
            likesRepository.deleteByUserAndBoard(user, board);
            return false;
        }
    }


    public boolean chkLike(User user, Board board) {
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
        likesRepository.deleteByBoard(board);
        commentRepository.deleteByBoard(board);
        boardRepository.deleteById(boardId);
    }


    @Transactional
    public Board uptBoard(Long boardId, BoardRequestDto boardRequestDto) {
        Board board = boardRepository.findOneByBoardId(boardId);
        board.update(boardRequestDto);
        return board;
    }

    public List<BoardListResponseDto> getMainBoardList(StockDto stockDto) {
        Stock stock = new Stock(stockDto);
        List<BoardListResponseDto> boardListResponseDto = boardRepository.findTop10ByStockOrderByCreatedAtDesc(stock);
        return boardListResponseDto;
    }


    public Board getBoardByBoardId(Long b) {
        return boardRepository.findByBoardId(b);
    }
}