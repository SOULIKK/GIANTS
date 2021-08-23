package com.spring.giants.service;


import com.spring.giants.config.exception.ApiRequestException;
import com.spring.giants.model.dto.CommentRequestDto;
import com.spring.giants.model.dto.CommentResponseDto;
import com.spring.giants.model.entity.Board;
import com.spring.giants.model.entity.Comment;
import com.spring.giants.model.entity.User;
import com.spring.giants.model.repository.BoardRepository;
import com.spring.giants.model.repository.CommentRepository;
import com.spring.giants.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {


    final private CommentRepository commentRepository;
    final private BoardRepository boardRepository;
    final private UserRepository userRepository;

    @Transactional
    public Board setComment(CommentRequestDto commentRequestDto, String username, Long boardId) {

        User user = userRepository.findByUsername(username);
        Board board = boardRepository.findByBoardId(boardId);

        Comment comment = new Comment(commentRequestDto, user, board);
        return commentRepository.save(comment).getBoard();

    }


}
