package com.spring.giants.controller;

import com.spring.giants.model.dto.BoardDetailResponseDto;
import com.spring.giants.model.dto.BoardListResponseDto;
import com.spring.giants.model.dto.BoardRequestDto;
import com.spring.giants.model.dto.StockDto;
import com.spring.giants.model.entity.Stock;
import com.spring.giants.service.BoardService;
import com.spring.giants.service.MainService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;


@Controller
@AllArgsConstructor
@RequestMapping("/board")
public class BoardController {


    final private BoardService boardService;
    final private MainService mainService;


    @GetMapping("/list")
    public String getStockBoard(
            @RequestParam String stockId
            , @RequestParam(required = false, defaultValue = "") String search
            , @PageableDefault(size = 10) Pageable pageable
            , Model model
    ) {

        StockDto stockDto = mainService.getStockByStockId(stockId);
        Page<BoardListResponseDto> boardListResponseDto = boardService.getBoardList(stockDto, search, pageable);

        int startPage = Math.max(1, boardListResponseDto.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boardListResponseDto.getTotalPages(), boardListResponseDto.getPageable().getPageNumber() + 4);

        model.addAttribute("boards", boardListResponseDto);
        model.addAttribute("stock", stockDto);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "board/list";
    }


    @GetMapping("/write")
    public String write(@RequestParam String stock, Model model) {
        StockDto stockDto = mainService.getStockByStockId(stock);

        BoardRequestDto boardRequestDto = new BoardRequestDto();
        model.addAttribute("stock", stockDto);
        model.addAttribute("board", boardRequestDto);
        return "board/write";
    }


    @PostMapping("/write")
    public String writeBoard(
            Authentication authentication
            , @RequestParam(value="stockId") String stockId
            , @Valid @ModelAttribute BoardRequestDto boardRequestDto
            , BindingResult bindingResult
            , RedirectAttributes redirectAttributes
            ) {

        String username = authentication.getName();

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("board", boardRequestDto);
            return "redirect:/board/write?stock="+stockId;
        }

        boardService.setBoard(username, stockId, boardRequestDto);
        return "redirect:/board/list?stockId="+stockId;
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

    @GetMapping("/update")
    public String updateForm(String s, Long b, Model model) {
        BoardDetailResponseDto board = boardService.getDetail(b);
        model.addAttribute("board", board);
        model.addAttribute("stockId", s);
        return "board/update";
    }

    @PostMapping("/update")
    public String update(Long boardId, BoardRequestDto boardRequestDto, Authentication authentication) {
        String username = authentication.getName();
        Stock stock = boardRequestDto.getStock();

        boardService.uptBoard(boardId, boardRequestDto);
        return "redirect:/board/detail?s="+stock.getStockId()+"&b="+boardId;
    }

}
