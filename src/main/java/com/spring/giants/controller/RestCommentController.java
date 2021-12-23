package com.spring.giants.controller;

import com.spring.giants.model.dto.CommentResponseDto;
import com.spring.giants.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RequiredArgsConstructor
@RestController
public class RestCommentController {

    private final CommentService commentService;


    @PostMapping("/comment/ep")
    public ResponseEntity<List<CommentResponseDto>> getCommentList(@RequestParam Long epId) {

        List<CommentResponseDto> commentResponseDtoList = commentService.getCommentList(epId);

        return new ResponseEntity<>(commentResponseDtoList, HttpStatus.OK);
    }

    @DeleteMapping("/comment/ep")
    public ResponseEntity<List<CommentResponseDto>> removeComment(@RequestParam Long epId, @RequestParam Long commentId) {
        commentService.delComment(commentId);
        List<CommentResponseDto> commentResponseDtoList = commentService.getCommentList(epId);

        return new ResponseEntity<>(commentResponseDtoList, HttpStatus.OK);
    }


}
