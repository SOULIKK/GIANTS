package com.spring.giants.controller;


import com.spring.giants.model.entity.Board;
import com.spring.giants.model.entity.User;
import com.spring.giants.service.BoardService;
import com.spring.giants.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    final private UserService userService;
    final private BoardService boardService;

    @GetMapping("/mypage")
    public String getUserpage(Authentication authentication, Model model) {

        String username = authentication.getName();
        User user = userService.getUserInfo(username);
        List<Board> userBoards = boardService.getUserBoard(user);

        model.addAttribute("userInfo", user);
        model.addAttribute("userBoards", userBoards);

        return "/user/mypage";
    }


}
