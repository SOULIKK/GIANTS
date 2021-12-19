package com.spring.giants.service;


import com.spring.giants.exception.ApiRequestException;
import com.spring.giants.model.dto.CommentRequestDto;
import com.spring.giants.model.dto.CommentResponseDto;
import com.spring.giants.model.entity.Board;
import com.spring.giants.model.entity.Comment;
import com.spring.giants.model.entity.EditorsPick;
import com.spring.giants.model.entity.User;
import com.spring.giants.model.repository.BoardRepository;
import com.spring.giants.model.repository.CommentRepository;
import com.spring.giants.model.repository.EditorsPickRepository;
import com.spring.giants.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class CommentService {


    final private CommentRepository commentRepository;
    final private BoardRepository boardRepository;
    final private EditorsPickRepository editorsPickRepository;
    final private UserRepository userRepository;

    @Transactional
    public Board setCommentBoard(CommentRequestDto commentRequestDto, String username, Long boardId) {

        if (commentRequestDto.getContent().isEmpty()) {
            new RuntimeException();
        }

        User user = userRepository.findByUsername(username);
        Board board = boardRepository.findByBoardId(boardId);

        Comment comment = new Comment(commentRequestDto, user, board, null);
        return commentRepository.save(comment).getBoard();
    }

    @Transactional
    public EditorsPick setCommentEp(CommentRequestDto commentRequestDto, String username, Long epId) {
        User user = userRepository.findByUsername(username);
        EditorsPick editorsPick = editorsPickRepository.findById(epId).orElseThrow(
                () -> new ApiRequestException("존재하지 않는 게시물입니다.")
        );
        Comment comment = new Comment(commentRequestDto, user, null, editorsPick);
        return commentRepository.save(comment).getEditorsPick();

    }

    @Transactional
    public void delComment(Long commentId) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new RuntimeException()
        );

        commentRepository.delete(comment);
    }

    @Transactional
    public List<CommentResponseDto> getCommentList(Long epId) {
        EditorsPick editorsPick = editorsPickRepository.findById(epId).orElseThrow(
                () -> new ApiRequestException("")
        );
        List<CommentResponseDto> commentResponseDto = commentRepository.findAllByEditorsPickOrderByCreatedAtDesc(editorsPick);
        return commentResponseDto;
    }
}
