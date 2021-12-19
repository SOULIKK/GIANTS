package com.spring.giants.controller;

import com.spring.giants.model.dto.CommentResponseDto;
import com.spring.giants.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@RestController
public class RestCommentController {

    private final CommentService commentService;


    @PostMapping("/comment/ep")
    public ResponseEntity<List<CommentResponseDto>> getCommentList(@RequestParam Long epId) {

        List<CommentResponseDto> result = new ArrayList<>();
        result = commentService.getCommentList(epId);

        List<CommentResponseDto> commentResponseDtoList = commentService.getCommentList(epId);

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

}
