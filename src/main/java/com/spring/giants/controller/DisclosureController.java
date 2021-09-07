package com.spring.giants.controller;


import com.spring.giants.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
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
