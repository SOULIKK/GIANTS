package com.spring.giants.controller;


import com.spring.giants.model.dto.BoardListResponseDto;
import com.spring.giants.model.dto.DisclosureResponseDto;
import com.spring.giants.model.dto.StockDto;
import com.spring.giants.service.BoardService;
import com.spring.giants.service.DisclosureService;
import com.spring.giants.service.MainService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@Controller
@AllArgsConstructor
public class StockController {

    private final MainService mainService;
    private final BoardService boardService;
    private final DisclosureService disclosureService;

    @GetMapping("/stock")
    public String stockMain() {
        return "main/stock";
    }


    @GetMapping("/main/stock")
    public String searchedValue(StockDto stockDto, Model model, Pageable pageable) {

        List<BoardListResponseDto> boardListResponseDto = boardService.getMainBoardList(stockDto);
        List<DisclosureResponseDto> disclosureResponseDto = disclosureService.getReport(stockDto);

        model.addAttribute("stock", stockDto);
        model.addAttribute("boards", boardListResponseDto);
        model.addAttribute("reports", disclosureResponseDto);

        return "main/stock";
    }
}
