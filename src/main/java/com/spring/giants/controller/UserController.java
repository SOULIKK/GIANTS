package com.spring.giants.controller;


import com.spring.giants.model.dto.PageRequestDto;
import com.spring.giants.model.entity.User;
import com.spring.giants.service.BoardService;
import com.spring.giants.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class UserController {

    final private UserService userService;
    final private BoardService boardService;

    @GetMapping("/bookmark")
    public String getMyBookMark(Authentication authentication, Model model, PageRequestDto pageRequestDto) {
        String username = authentication.getName();
        model.addAttribute("myBookMarks", userService.getMyBookMark(username, pageRequestDto));
        return "mypage/bookmark";
    }

    @GetMapping("/contents")
    public String getMyContents(Authentication authentication, Model model, PageRequestDto pageRequestDto) {
        final String boardType = "MY_BOARD";
        model.addAttribute("myContents", boardService.getList(boardType, authentication.getName(), "", pageRequestDto));
        model.addAttribute("username", authentication.getName());
        return "mypage/contents";
    }

    @GetMapping("/likes")
    public String getMyLikes(Authentication authentication, Model model, PageRequestDto pageRequestDto) {
        final String boardType = "MY_LIKES";
        model.addAttribute("myLikes", boardService.getList(boardType, authentication.getName(), "", pageRequestDto));
        model.addAttribute("username", authentication.getName());
        return "mypage/likes";
    }


    @GetMapping("/profile")
    public String getMyProfile(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userService.getUser(username);
        model.addAttribute("username", user.getUsername());
        return "mypage/profile";
    }


    @PostMapping("/updatePw")
    public String updatePw(Authentication authentication, String password, String chkPassword) {

        if (!password.equals(chkPassword)) {
            return "redirect:/mypage/contens";
        }

        String username = authentication.getName();
        userService.updatePw(username, password);

        return "redirect:/mypage";
    }


}
