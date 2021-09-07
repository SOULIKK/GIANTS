package com.spring.giants.controller;


import com.spring.giants.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DisclosureController {

    private final BoardService boardService;

    @GetMapping("/disclosure/all")
    public String allDisclosure() {
        return "/disclosure/list";

    }

}
