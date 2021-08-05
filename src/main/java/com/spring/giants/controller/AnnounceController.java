package com.spring.giants.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/announce")
public class AnnounceController {

    @GetMapping("/main")
    public String announce() {
        return "main/announce";
    }
}
