package com.spring.giants.controller;

import com.spring.giants.model.dto.*;
import com.spring.giants.model.entity.Board;
import com.spring.giants.service.BoardService;
import com.spring.giants.service.DisclosureService;
import com.spring.giants.service.MainService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.List;


@Controller
@AllArgsConstructor
public class MainController {

    final private BoardService boardService;
    final private DisclosureService disclosureService;

    @GetMapping("/")
    public String main(Model model, PageRequestDto pageRequestDto) {
        String state = "HOME";
        model.addAttribute("state", state);
        final String boardType = "HOT_BOARD";
        List<DisclosureResponseDto> todayReports = disclosureService.getMainReport();
        model.addAttribute("todayReports", todayReports);
        model.addAttribute("boards", boardService.getList(boardType, "", "", pageRequestDto));
        return "/main/main";
    }

    @GetMapping("/main/search")
    public String stockSearch() {
        return "/main/search";
    }

    @GetMapping("/main/hot")
    public String hot(@RequestParam(required = false, defaultValue = "") String keyword, PageRequestDto pageRequestDto, Model model) throws ParseException {
        final String boardType = "HOT_BOARD";
        model.addAttribute("boards", boardService.getList(boardType, "", "", pageRequestDto));
        model.addAttribute("state", "HOT");
        return "/main/hot";
    }



    @GetMapping("/sample")
    public String iframeSample() {
        return "pick/detail";
    }

}
