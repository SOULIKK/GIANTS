package com.spring.giants.controller;


import com.spring.giants.model.dto.RoleResponseDto;
import com.spring.giants.model.entity.Role;
import com.spring.giants.model.entity.User;
import com.spring.giants.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class AdminController {

    final private UserService userService;

    @GetMapping("/admin/dashboard")
    public String dashboard() {
        return "user/dashboard";
    }

    @GetMapping("/main/pick")
    public String pick(Model model, Authentication authentication) {
        String state = "pick";

        String username = authentication.getName();

        model.addAttribute("username", username);
        model.addAttribute("state", state);
        return "/main/pick";
    }


}
