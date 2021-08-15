package com.spring.giants.controller;


import com.spring.giants.model.dto.StockResponseDto;
import com.spring.giants.service.MainService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class MainController {

    final private MainService mainService;

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

    @PostMapping("/main/stock")
    public String searchedValue(String stock, Model model) {
        System.out.println(stock);
        StockResponseDto stockResponseDto = mainService.getStockId(stock);
        model.addAttribute("stock", stockResponseDto);
        return "main/stock";
    }

}
