package com.spring.giants.controller;


import com.spring.giants.config.validator.BoardValidator;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.function.DoubleToIntFunction;


@Controller
@AllArgsConstructor
@RequestMapping("/board")
public class BoardController {


    final private BoardService boardService;
    final private MainService mainService;
    final private BoardValidator boardValidator;


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
        model.addAttribute("board", new BoardRequestDto());
        return "board/write";
    }


    @PostMapping("/write")
    public String writeBoard(Authentication authentication, @Valid BoardRequestDto boardRequestDto, BindingResult bindingResult) {
        boardValidator.validate(boardRequestDto, bindingResult);

        String username = authentication.getName();
        String stock = boardService.setBoard(username, boardRequestDto);

        if (bindingResult.hasErrors()) {
            return "redirect:/board/list?stock="+stock;
        }

        return "redirect:/board/list?stock="+stock;
    }



    @GetMapping("/detail")
    public String getBoardDetail(@RequestParam String s, @RequestParam Long b,  Authentication authentication, Model model) {

        BoardDetailResponseDto boardDetailResponseDto = boardService.getDetail(b);
        String username = authentication.getName();
        boolean isLiked = boardService.chkLike(username, b);
        int countComment = boardDetailResponseDto.getComments().size();
        int countLikes = boardDetailResponseDto.getLikes().size();

        model.addAttribute("user", username);
        model.addAttribute("board", boardDetailResponseDto);
        model.addAttribute("countComment", countComment);
        model.addAttribute("countLikes", countLikes);
        model.addAttribute("isLiked", isLiked);
        model.addAttribute("stockId", s);

        return "board/detail";
    }

    @GetMapping("/like")
    public String like(@RequestParam String s, @RequestParam Long b, Authentication authentication, Model model) {
        String username = authentication.getName();
        boolean isLiked = boardService.setLike(username, b);
        model.addAttribute("isLiked", isLiked);
        return "redirect:detail?s="+s+"&b="+b;
    }


    @PostMapping("/delete")
    public String delete(String stockId, Long boardId) {
        boardService.delBoard(boardId);
        return "redirect:list?stock="+stockId;
    }




}
