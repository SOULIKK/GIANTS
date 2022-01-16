package com.spring.giants.service;


import com.spring.giants.exception.ApiRequestException;
import com.spring.giants.model.dto.CommentRequestDto;
import com.spring.giants.model.dto.CommentResponseDto;
import com.spring.giants.model.entity.*;
import com.spring.giants.model.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
public class CommentService {


    final private CommentRepository commentRepository;
    final private BoardRepository boardRepository;
    final private EditorsPickRepository editorsPickRepository;
    final private UserRepository userRepository;
    final private DisclosureRepository disclosureRepository;

    @Transactional
    public Board setCommentBoard(CommentRequestDto commentRequestDto, String username, Long boardId) {

        if (commentRequestDto.getContent().isEmpty()) {
            new RuntimeException();
        }

        User user = userRepository.findByUsername(username);
        Board board = boardRepository.findByBoardId(boardId);

        Comment comment = new Comment(commentRequestDto, user, board, null, null);
        return commentRepository.save(comment).getBoard();
    }

    @Transactional
    public EditorsPick setCommentEp(CommentRequestDto commentRequestDto, String username, Long epId) {
        User user = userRepository.findByUsername(username);
        EditorsPick editorsPick = editorsPickRepository.findById(epId).orElseThrow(
                () -> new ApiRequestException("존재하지 않는 게시물입니다.")
        );
        Comment comment = new Comment(commentRequestDto, user, null, editorsPick, null);
        return commentRepository.save(comment).getEditorsPick();

    }

    @Transactional
    public void delComment(Long commentId) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new RuntimeException()
        );

        commentRepository.delete(comment);
    }


    public List<CommentResponseDto> getCommentList(Long epId) {
        EditorsPick editorsPick = editorsPickRepository.findById(epId).orElseThrow(
                () -> new ApiRequestException("존재하지 않는 게시글입니다.")
        );
        List<CommentResponseDto> commentResponseDto = commentRepository.findAllByEditorsPickOrderByCreatedAtDesc(editorsPick);
        return commentResponseDto;
    }

    @Transactional
    public Disclosure setCommentDisclosure(CommentRequestDto commentRequestDto, String username, String rcpNo) {
        User user = userRepository.findByUsername(username);
        Disclosure disclosure = disclosureRepository.findById(rcpNo).orElseThrow(
                () -> new ApiRequestException("존재하지 않는 공시입니다.")
        );
        Comment comment = new Comment(commentRequestDto, user, null, null, disclosure);
        return commentRepository.save(comment).getDisclosure();
    }
}
