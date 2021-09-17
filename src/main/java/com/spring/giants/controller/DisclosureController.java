package com.spring.giants.controller;


import com.spring.giants.model.dto.DisclosureResponseDto;
import com.spring.giants.service.BoardService;
import com.spring.giants.service.DisclosureService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class DisclosureController {

    private final DisclosureService disclosureService;


    @GetMapping("/report/{reportType}")
    public String getReport(@PathVariable String reportType, @PageableDefault(size = 100) Pageable pageable, Model model) {

        Page<DisclosureResponseDto> disclosureResponseDto = disclosureService.getTodayReport(reportType, pageable);

        int startPage = Math.max(1, disclosureResponseDto.getPageable().getPageNumber() - 4);
        int endPage = Math.min(disclosureResponseDto.getTotalPages(), disclosureResponseDto.getPageable().getPageNumber() + 4);

        model.addAttribute("todayReports", disclosureResponseDto);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("reportType", reportType);

        return "/disclosure/list";
    }



}
