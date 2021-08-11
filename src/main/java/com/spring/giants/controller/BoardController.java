package com.spring.giants.controller;


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

import java.util.function.DoubleToIntFunction;


@Controller
@AllArgsConstructor
@RequestMapping("/board")
public class BoardController {

    final private BoardRepository boardRepository;
    final private BoardService boardService;
    // final private BoardValidator boardValidator;

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


    @GetMapping("/write")
    public String write() {
        return "board/write";
    }


    @PostMapping("/write")
    public String writeBoard(Authentication authentication, BoardRequestDto boardRequestDto) {
        String username = authentication.getName();
        boardService.setBoard(username, boardRequestDto);
        return "board/list";
    }


    @GetMapping("/search")
//    @PostMapping("/search/{stockName}")
//    public String searchedValue(@PathVariable String stockName, Model model) {
    public String searchedValue(@RequestParam String stock, Model model) {
        System.out.println(stock);
        StockResponseDto stockResponseDto = boardService.getStockCode(stock);
        model.addAttribute("stock", stockResponseDto);

//        return "board/list/"+stockResponseDto.getStockCode();
        return "main/stock";
    }

}
