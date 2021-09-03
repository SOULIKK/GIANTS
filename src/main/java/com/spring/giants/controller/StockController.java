package com.spring.giants.controller;


import com.spring.giants.model.dto.StockResponseDto;
import com.spring.giants.service.MainService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@AllArgsConstructor
public class StockController {

    private final MainService mainService;

    @GetMapping("/stock")
    public String stockMain() {
        return "main/stock";
    }


    @GetMapping("/main/stock")
    public String searchedValue(@RequestParam String name, Model model) {
        String id = "";
        StockResponseDto stockResponseDto = mainService.getStock(name, id);
        model.addAttribute("stock", stockResponseDto);
        return "main/stock";
    }
}
