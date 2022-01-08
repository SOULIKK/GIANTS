package com.spring.giants.controller;


import com.spring.giants.model.dto.BoardListResponseDto;
import com.spring.giants.model.dto.DisclosureResponseDto;
import com.spring.giants.model.dto.StockDto;
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

    @GetMapping("/main/stock")
    public String searchedValue(String stockName, Model model) {

        List<StockDto> stockDto = mainService.getStockByStockName(stockName);

        if (stockDto.size() == 1) {
            StockDto stockDto1 = new StockDto(stockDto.get(0).getStockId(), stockDto.get(0).getStockName(), stockDto.get(0).getMarket(), stockDto.get(0).getSector(), stockDto.get(0).getProduct(), stockDto.get(0).getHomepage());
            List<BoardListResponseDto> boardListResponseDto = boardService.getMainBoardList(stockDto.get(0));
            List<DisclosureResponseDto> disclosureResponseDto = disclosureService.getReport(stockDto.get(0).getStockId());

            model.addAttribute("stock", stockDto1);
            model.addAttribute("boards", boardListResponseDto);
            model.addAttribute("reports", disclosureResponseDto);

            return "main/stock";
        }
        if (stockDto.isEmpty()) {
            model.addAttribute("key", stockName);
            model.addAttribute("stockList", "none");
            return "main/searchedPage";
        }
        model.addAttribute("stockList", stockDto);
        return "main/searchedPage";
    }
}
