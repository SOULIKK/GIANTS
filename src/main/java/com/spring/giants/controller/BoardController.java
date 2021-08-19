package com.spring.giants.controller;


import com.spring.giants.model.dto.BoardDetailResponseDto;
import com.spring.giants.model.dto.BoardListResponseDto;
import com.spring.giants.model.dto.BoardRequestDto;
import com.spring.giants.model.dto.StockResponseDto;
import com.spring.giants.model.entity.Board;
import com.spring.giants.model.entity.Likes;
import com.spring.giants.model.entity.Stock;
import com.spring.giants.model.repository.BoardRepository;

import com.spring.giants.service.BoardService;
import com.spring.giants.service.MainService;
import lombok.AllArgsConstructor;
import lombok.experimental.PackagePrivate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.function.DoubleToIntFunction;


@Controller
@AllArgsConstructor
@RequestMapping("/board")
public class BoardController {


    final private BoardService boardService;
    final private MainService mainService;


    @GetMapping("/list")
    public String getStockBoard(
            @RequestParam String stock
            , @RequestParam(required = false, defaultValue = "") String search
            , @PageableDefault(size = 10) Pageable pageable
            , Model model
    ) {
        String stockName = "";
        StockResponseDto stockResponseDto = mainService.getStock(stockName, stock);

        Page<BoardListResponseDto> boardListResponseDto = boardService.getBoardList(stockResponseDto.getStockId(), search, pageable);

        int startPage = Math.max(1, boardListResponseDto.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boardListResponseDto.getTotalPages(), boardListResponseDto.getPageable().getPageNumber() + 4);

        model.addAttribute("boards", boardListResponseDto);
        model.addAttribute("stock", stockResponseDto);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "board/list";
    }


    @GetMapping("/write")
    public String write(@RequestParam String stock, Model model) {
        model.addAttribute("stock", stock);
        return "board/write";
    }


    @PostMapping("/submit")
    public String writeBoard(Authentication authentication, BoardRequestDto boardRequestDto) {
        String username = authentication.getName();
        System.out.println(boardRequestDto.getStockId());
        String stock = boardService.setBoard(username, boardRequestDto);

        return "redirect:/board/list?stock="+stock;
    }



    @GetMapping("/detail")
    public String getBoardDetail(@RequestParam Long b,  Authentication authentication, Model model) {

        BoardDetailResponseDto boardDetailResponseDto = boardService.getDetail(b);
        String username = authentication.getName();
        boolean isLiked = boardService.chkLike(username, b);

        model.addAttribute("board", boardDetailResponseDto);
        model.addAttribute("isLiked", isLiked);

        return "board/detail";
    }

    @GetMapping("/like")
    public String like(@RequestParam Long b, Authentication authentication, Model model) {
        String username = authentication.getName();
//        System.out.println("username ::::::::::::::::::::::::: "+username);
        boolean isLiked = boardService.setLike(username, b);
//        System.out.println("isLiked :::::::::::::::::::::::::  "+isLiked);
        model.addAttribute("isLiked", isLiked);
        return "redirect:detail?b="+b;
    }

}
