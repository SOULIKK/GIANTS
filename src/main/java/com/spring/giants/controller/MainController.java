package com.spring.giants.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String main() {
        return "main/main";
    }

    @GetMapping("/main/search")
    public String stockSearch() {
        return "main/search";
    }
}
