package com.spring.giants.controller;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@AllArgsConstructor
@RequestMapping("/stock")
public class StockController {


    @GetMapping("/")
    public String stockMain() {
        return "main/stock";
    }
}
