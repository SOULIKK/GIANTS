package com.spring.giants.controller;


import com.spring.giants.model.dto.BoardListResponseDto;
import com.spring.giants.model.dto.StockResponseDto;
import com.spring.giants.service.BoardService;
import com.spring.giants.service.MainService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@AllArgsConstructor
public class StockController {

    private final MainService mainService;
    private final BoardService boardService;

    @GetMapping("/stock")
    public String stockMain() {
        return "main/stock";
    }


    @GetMapping("/main/stock")
    public String searchedValue(@RequestParam String name, Model model, Pageable pageable) {
        String id = "";
        StockResponseDto stockResponseDto = mainService.getStock(name, id);

        List<BoardListResponseDto> boardListResponseDto = boardService.getMainBoardList(stockResponseDto.getStockId());

        model.addAttribute("stock", stockResponseDto);
        model.addAttribute("boards", boardListResponseDto);

        return "main/stock";
    }
}
