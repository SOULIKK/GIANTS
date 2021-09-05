package com.spring.giants.controller;

import com.spring.giants.service.MainService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;




@Controller
@AllArgsConstructor
public class MainController {

    final private MainService mainService;

    @GetMapping("/")
    public String main(Model model) {
        String state = "home";
        model.addAttribute("state", state);
        return "/main/main";
    }

    @GetMapping("/main/search")
    public String stockSearch() {
        return "/main/search";
    }

    @GetMapping("/main/hot")
    public String hot(Model model) {
        String state = "hot";
        model.addAttribute("state", state);
        return "/main/hot";
    }

    @GetMapping("/main/pick")
    public String pick(Model model) {
        String state = "pick";
        model.addAttribute("state", state);
        return "/main/pick";
    }


}
