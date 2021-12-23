package com.spring.giants.controller;


import com.spring.giants.model.dto.CommentRequestDto;
import com.spring.giants.model.dto.CommentResponseDto;
import com.spring.giants.model.entity.Board;
import com.spring.giants.model.entity.EditorsPick;
import com.spring.giants.service.BoardService;
import com.spring.giants.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/create/{boardId}")
    public String setComment(@PathVariable Long boardId, Authentication authentication, CommentRequestDto commentRequestDto) {

        String username = authentication.getName();
        Board board = commentService.setCommentBoard(commentRequestDto, username, boardId);
        return "redirect:/board/detail?s="+board.getStock().getStockId()+"&b="+board.getBoardId();
    }

    @PostMapping("/create/ep/{epId}")
    public String setEpComment(@PathVariable Long epId, Authentication authentication, CommentRequestDto commentRequestDto) {
        String username = authentication.getName();
        commentService.setCommentEp(commentRequestDto, authentication.getName(), epId);
        return "redirect:/pick";
    }

    @PostMapping("/delete")
    public String deleteComment(String stockId, Long boardId, Long commentId) {
        commentService.delComment(commentId);
        return "redirect:/board/detail?s="+stockId+"&b="+boardId;
    }


}
