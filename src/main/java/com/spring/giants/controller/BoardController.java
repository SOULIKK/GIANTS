package com.spring.giants.controller;


import com.spring.giants.model.dto.BoardListResponseDto;
import com.spring.giants.model.dto.BoardRequestDto;
import com.spring.giants.model.dto.StockResponseDto;
import com.spring.giants.model.entity.Board;
import com.spring.giants.model.entity.Stock;
import com.spring.giants.model.repository.BoardRepository;

import com.spring.giants.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.experimental.PackagePrivate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.DoubleToIntFunction;


@Controller
@AllArgsConstructor
@RequestMapping("/board")
public class BoardController {

    final private BoardRepository boardRepository;
    final private BoardService boardService;

    @GetMapping("/list")
    public String getList(
            Model model
            , @RequestParam(required = false, defaultValue = "") String search
            , @PageableDefault(size = 5) Pageable pageable) {

        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(search, search, pageable);

        int firstPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
        int lastPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4);
        model.addAttribute("firstPage", firstPage);
        model.addAttribute("listPage", lastPage);
        model.addAttribute("boards", boards);

        return "board/list";
    }


    @PostMapping("/write")
    public String write(String stock, Model model) {
        model.addAttribute("stock", stock);
        return "board/write";
    }


    @PostMapping("/submit")
    public String writeBoard(Authentication authentication, BoardRequestDto boardRequestDto) {
        String username = authentication.getName();
        System.out.println(boardRequestDto.getStockId());
        boardService.setBoard(username, boardRequestDto);
        return "board/stock";
    }


    @PostMapping("/stock")
    public String getStockBoard(String stock, Model model) {
        System.out.println(stock);
//        List<BoardListResponseDto> boardListResponseDto = boardService.getBoardList(stock);
//        BoardListResponseDto boardListResponseDto = boardService.getBoardList(stock);
//        model.addAttribute("boards", boardListResponseDto);
        model.addAttribute("stock", stock);
        return "board/stock";
    }




}
