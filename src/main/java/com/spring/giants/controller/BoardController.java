package com.spring.giants.controller;

import com.spring.giants.model.dto.*;
import com.spring.giants.model.entity.Board;
import com.spring.giants.model.entity.User;
import com.spring.giants.service.BoardService;
import com.spring.giants.service.MainService;
import com.spring.giants.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    final private UserService userService;

    @GetMapping("/list")
    public String getStockBoard(
            @RequestParam String stock
            , @RequestParam(required = false, defaultValue = "") String keyword
            , Model model
            , PageRequestDto pageRequestDto
    ) {

        final String boardType = "STOCK_BOARD";
        StockDto stockDto = mainService.getStockByStockId(stock);

        model.addAttribute("boards", boardService.getList(boardType, "", stock, pageRequestDto));
        model.addAttribute("pageRequestDto", pageRequestDto);
        model.addAttribute("stock", stockDto);

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
        return "redirect:/board/list?stock="+stockId;
    }



    @GetMapping("/detail")
    public String getBoardDetail(@RequestParam Long b,  Authentication authentication, Model model) {

        BoardDetailResponseDto boardDetailResponseDto = boardService.getDetail(b);
        String username = authentication.getName();
        User user = userService.getUser(username);
        Board board = boardService.getBoardByBoardId(b);

        boolean isLiked = boardService.chkLike(user, board);
        int countComment = boardDetailResponseDto.getComments().size();
        int countLikes = boardDetailResponseDto.getLikes().size();

        model.addAttribute("username", username);
        model.addAttribute("board", boardDetailResponseDto);
        model.addAttribute("countComment", countComment);
        model.addAttribute("countLikes", countLikes);
        model.addAttribute("isLiked", isLiked);


        return "board/detail";
    }

    @ResponseBody
    @PostMapping("/like")
    public int like(@RequestParam String s, @RequestParam Long b, Authentication authentication) {
        String username = authentication.getName();
        boolean isLiked = boardService.setLike(username, b);
        BoardDetailResponseDto boardDetailResponseDto = boardService.getDetail(b);
        int countLikes = boardDetailResponseDto.getLikes().size();
        return countLikes;
    }


    @PostMapping("/delete/{boardId}")
    public String delete(@PathVariable Long boardId, @RequestParam(value="stockId", required=false) String stockId) {
        boardService.delBoard(boardId);
        System.out.println("stockId = " + stockId);
        return "redirect:/board/list?stock="+stockId;
    }

    @GetMapping("/update/{boardId}")
    public String updateForm(@PathVariable Long boardId, Model model) {
        BoardDetailResponseDto board = boardService.getDetail(boardId);
        model.addAttribute("board", board);
        return "board/update";
    }

    @PostMapping("/update")
    public String update(Long boardId, BoardRequestDto boardRequestDto) {
        Board board = boardService.uptBoard(boardId, boardRequestDto);
        return "redirect:/board/detail?b="+board.getBoardId();
    }

}
