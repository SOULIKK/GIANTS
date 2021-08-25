package com.spring.giants.controller;


import com.spring.giants.model.dto.StockResponseDto;
import com.spring.giants.service.MainService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/search")
    public String stockSearch() {
        return "/main/search";
    }

    @GetMapping("/hot")
    public String hot(Model model) {
        String state = "hot";
        model.addAttribute("state", state);
        return "/main/hot";
    }

    @GetMapping("/pick")
    public String pick(Model model) {
        String state = "pick";
        model.addAttribute("state", state);
        return "/main/pick";
    }

    @GetMapping("/main/stock")
    public String searchedValue(@RequestParam String name, Model model) {
        String id = "";
        StockResponseDto stockResponseDto = mainService.getStock(name, id);
        model.addAttribute("stock", stockResponseDto);
        return "main/stock";
    }

}
