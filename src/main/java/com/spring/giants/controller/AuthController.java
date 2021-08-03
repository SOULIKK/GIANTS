package com.spring.giants.controller;


import com.spring.giants.model.entity.User;
import com.spring.giants.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    final private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/join")
    public String join() {
        return "auth/join";
    }

    @PostMapping("/join")
    public String setJoin(User user) {
        userService.join(user);
        return "redirect:/";
    }
}
