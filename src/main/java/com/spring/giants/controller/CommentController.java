package com.spring.giants.controller;


import com.spring.giants.model.dto.BoardDetailResponseDto;
import com.spring.giants.model.dto.CommentRequestDto;
import com.spring.giants.model.entity.Board;
import com.spring.giants.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;


    @PostMapping("/create/{boardId}")
    public String setComment(@PathVariable Long boardId, Authentication authentication, @RequestBody CommentRequestDto commentRequestDto) {
        String username = authentication.getName();
        Board board = commentService.setComment(commentRequestDto, username, boardId);

        return "redirect:/board/detail?s="+board.getStockId()+"&b="+board.getBoardId();
    }


}
