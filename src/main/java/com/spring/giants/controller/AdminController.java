package com.spring.giants.controller;

import com.spring.giants.model.dto.BoardRequestDto;
import com.spring.giants.service.BoardService;
import com.spring.giants.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class AdminController {

    final private UserService userService;
    final private BoardService boardService;

    @GetMapping("/admin/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/admin/write")
    public String write() {
        return "admin/write";
    }

    @PostMapping("/admin/submit")
    public String submit(BoardRequestDto boardRequestDto, Authentication authentication) {
        String username = authentication.getName();
        boardService.setBoard(username, boardRequestDto);
        return "redirect:/main/pick";
    }

}
