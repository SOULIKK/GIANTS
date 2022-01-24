package com.spring.giants.controller;

import com.spring.giants.model.dto.PageRequestDto;
import com.spring.giants.model.dto.StockDto;
import com.spring.giants.model.repository.DisclosureRepository;
import com.spring.giants.service.CommentService;
import com.spring.giants.service.DisclosureService;
import com.spring.giants.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class DisclosureController {

    private final MainService mainService;
    private final DisclosureService disclosureService;
    private final CommentService commentService;

    @GetMapping("/disclosure/{disclosureType}")
    public String getDisclosure(
            @PathVariable String disclosureType
            , @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate ss
            , @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate se
            , PageRequestDto pageRequestDto
            , Model model
    ) {

        model.addAttribute("reports", disclosureService.getDislosureList(disclosureType, ss, se, pageRequestDto));
        model.addAttribute("disclosureType", disclosureType);
        model.addAttribute("searchStart", ss);
        model.addAttribute("searchEnd", se);
        model.addAttribute("type", pageRequestDto.getType());
        model.addAttribute("keyword", pageRequestDto.getKeyword());

        return "disclosure/list";
    }

    @GetMapping("/stock/{stockId}/disclosure/{disclosureType}")
    public String getStockDisclosure(
            @PathVariable String stockId
            , @PathVariable String disclosureType
            , @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate ss
            , @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate se
            , PageRequestDto pageRequestDto
            , Model model
    ) {
        StockDto stockDto = mainService.getStockByStockId(stockId);
        model.addAttribute("reports", disclosureService.getStockDisclosure(stockId, disclosureType, ss, se, pageRequestDto));
        model.addAttribute("disclosureType", disclosureType);
        model.addAttribute("searchStart", ss);
        model.addAttribute("searchEnd", se);
        model.addAttribute("keyword", pageRequestDto.getKeyword());
        model.addAttribute("stock", stockDto);
        return "disclosure/list";
    }

    @GetMapping("/disclosure/detail/{rcpNo}")
    public String detail(@PathVariable String rcpNo, Model model) {
        model.addAttribute("comments", commentService.getDisclosureComments(rcpNo));
        model.addAttribute("rcpNo", rcpNo);
        return "disclosure/detail";
    }


}
