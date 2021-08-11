package com.spring.giants.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String main() {
        return "main/main";
    }

    @GetMapping("/search")
    public String stockSearch() {
        return "main/search";
    }

    @GetMapping("/hot")
    public String hot() {
        return "main/hot";
    }

    @GetMapping("/pick")
    public String pick() {
        return "main/pick";
    }

}
