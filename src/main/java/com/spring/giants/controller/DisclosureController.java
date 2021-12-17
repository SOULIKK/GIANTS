package com.spring.giants.controller;

import com.spring.giants.model.dto.DisclosureResponseDto;
import com.spring.giants.model.dto.StockDto;
import com.spring.giants.service.DisclosureService;
import com.spring.giants.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;;
import java.util.Date;

@Controller
@RequiredArgsConstructor
public class DisclosureController {

    private final MainService mainService;
    private final DisclosureService disclosureService;

    @GetMapping("/report/{reportType}")
    public String getReport(
            @PathVariable String reportType
            , @PageableDefault(size = 15) Pageable pageable
            , Model model
    ) {

        Page<DisclosureResponseDto> disclosureResponseDto = disclosureService.getTodayReports(reportType, pageable);

        int startPage = Math.max(1, disclosureResponseDto.getPageable().getPageNumber() - 4);
        int endPage = Math.min(disclosureResponseDto.getTotalPages(), disclosureResponseDto.getPageable().getPageNumber() + 4);

        model.addAttribute("reports", disclosureResponseDto);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("reportType", reportType);

        return "disclosure/list";
    }

    @PostMapping("/report/{search}")
    public String searchedReport(
            @PathVariable String search
            , String searchType
            , @DateTimeFormat(pattern = "yyyy-MM-dd") Date searchStart
            , @DateTimeFormat(pattern = "yyyy-MM-dd") Date searchEnd
            , String searchText
            , @PageableDefault(size = 15) Pageable pageable
            , Model model) {

        if (searchType.equals("corpName")) {
            StockDto stockDto = mainService.stockSearch(searchText);
            model.addAttribute("stock", stockDto);
        }

        Page<DisclosureResponseDto> disclosureResponseDto = disclosureService.getSearchedReports(searchType, searchStart, searchEnd, searchText, pageable);
        int startPage = Math.max(1, disclosureResponseDto.getPageable().getPageNumber() - 4);
        int endPage = Math.min(disclosureResponseDto.getTotalPages(), disclosureResponseDto.getPageable().getPageNumber() + 4);

        model.addAttribute("reports", disclosureResponseDto);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("reportType", search);

        return "disclosure/list";
    }

    @GetMapping("/report/s/{stockId}/{reportType}")
    public String stockReport(@PathVariable String stockId, @PathVariable String reportType, Pageable pageable, Model model) {

        Page<DisclosureResponseDto> disclosureResponseDto = disclosureService.getStockReports(stockId, reportType, pageable);
        model.addAttribute("reports", disclosureResponseDto);
        int startPage = Math.max(1, disclosureResponseDto.getPageable().getPageNumber() - 4);
        int endPage = Math.min(disclosureResponseDto.getTotalPages(), disclosureResponseDto.getPageable().getPageNumber() + 4);
        StockDto stockDto = mainService.getStockByStockId(stockId);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("reportType", reportType);
        model.addAttribute("stock", stockDto);

        return "disclosure/list";
    }


}
