package com.spring.giants.controller;

import com.spring.giants.model.dto.*;
import com.spring.giants.service.BoardService;
import com.spring.giants.service.DisclosureService;
import com.spring.giants.service.MainService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
@AllArgsConstructor
public class StockController {

    private final MainService mainService;
    private final BoardService boardService;
    private final DisclosureService disclosureService;

    @GetMapping("/stock")
    public String getStock(String stockName, Model model) {

        int countEqualStockName = mainService.getCountEqualStockName(stockName);

        if (countEqualStockName == 1) {
            StockDto stockDto1 = mainService.getStockByStockName(stockName);

            List<StockMainBoardDto> boards = boardService.getStockMainBoard(stockDto1);
            List<DisclosureResponseDto> disclosureResponseDto = disclosureService.getReport(stockDto1.getStockId());

            model.addAttribute("stock", stockDto1);
            model.addAttribute("boards", boards);
            model.addAttribute("reports", disclosureResponseDto);

            return "main/stock";
        }

        List<StockDto> stockDtoList = mainService.getStockListByStockName(stockName);
        if (stockDtoList.isEmpty()) {
            model.addAttribute("key", stockName);
            model.addAttribute("stockList", "none");
            return "main/searchedPage";
        }
        model.addAttribute("stockList", stockDtoList);
        return "main/searchedPage";

    }
}
