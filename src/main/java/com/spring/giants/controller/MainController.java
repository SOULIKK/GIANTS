package com.spring.giants.controller;

import com.spring.giants.model.dto.BoardListResponseDto;
import com.spring.giants.model.dto.BoardRequestDto;
import com.spring.giants.model.dto.DisclosureResponseDto;
import com.spring.giants.model.entity.Board;
import com.spring.giants.service.BoardService;
import com.spring.giants.service.DisclosureService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.List;


@Controller
@AllArgsConstructor
public class MainController {

    final private BoardService boardService;
    final private DisclosureService disclosureService;

    @GetMapping("/")
    public String main(Model model) throws ParseException {
        String state = "HOME";
        model.addAttribute("state", state);
        List<DisclosureResponseDto> todayReports = disclosureService.getMainReport();
        model.addAttribute("todayReports", todayReports);
        return "/main/main";
    }

    @GetMapping("/main/search")
    public String stockSearch() {
        return "/main/search";
    }

    @GetMapping("/main/hot")
    public String hot(Model model) throws ParseException {
        String state = "HOT";
        List<Board> boards = boardService.getHotBoards();

        model.addAttribute("boards", boards);
        model.addAttribute("state", state);

        return "/main/hot";
    }

    @GetMapping("/main/pick")
    public String pick(
            BoardRequestDto boardRequestDto
            , @RequestParam(required = false, defaultValue = "") String search
            , @PageableDefault(size = 10) Pageable pageable
            , Model model
    ) {

        String state = "PICK";
        String stockId = "EP";
        Page<BoardListResponseDto> boardListResponseDto = boardService.getBoardList(stockId, search, pageable);

        int startPage = Math.max(1, boardListResponseDto.getPageable().getPageNumber());
        int endPage = Math.min(boardListResponseDto.getTotalPages(), boardListResponseDto.getPageable().getPageNumber() + 4);

        model.addAttribute("picks", boardListResponseDto);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("state", state);

        return "main/pick";
    }

}
