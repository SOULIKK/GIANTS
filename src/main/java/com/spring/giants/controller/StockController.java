package com.spring.giants.controller;


import com.spring.giants.model.dto.BoardListResponseDto;
import com.spring.giants.model.dto.DisclosureResponseDto;
import com.spring.giants.model.dto.StockDto;
import com.spring.giants.model.entity.Stock;
import com.spring.giants.service.BoardService;
import com.spring.giants.service.DisclosureService;
import com.spring.giants.service.MainService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
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


    @GetMapping("/main/stock")
    public String searchedValue(String stockName, Model model) {

        Stock stock = mainService.getStockByStockName(stockName);

        List<BoardListResponseDto> boardListResponseDto = boardService.getMainBoardList(stock);
        List<DisclosureResponseDto> disclosureResponseDto = disclosureService.getReport(stock.getStockId());

        model.addAttribute("stock", stock);
        model.addAttribute("boards", boardListResponseDto);
        model.addAttribute("reports", disclosureResponseDto);

        return "main/stock";
    }
}
