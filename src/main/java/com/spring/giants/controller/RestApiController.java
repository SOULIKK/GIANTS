package com.spring.giants.controller;

import com.spring.giants.model.dto.CommentResponseDto;
import com.spring.giants.service.CommentService;
import com.spring.giants.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RequiredArgsConstructor
@RestController
public class RestApiController {

    private final CommentService commentService;
    private final UserService userService;


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

    @PostMapping("/cert/send")
    public ResponseEntity<String> sendMail(@RequestParam String email) {
        System.out.println("emailemailemailemailemail = " + email);
        userService.sendMail(email);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @PostMapping("/cert/check")
    public ResponseEntity<Boolean> chkCert(@RequestParam String email, @RequestParam int certKey) {
        Boolean chkCert = userService.chkCert(email, certKey);
        return new ResponseEntity<>(chkCert, HttpStatus.OK);
    }



}
