package com.spring.giants.controller;


import com.spring.giants.model.entity.Board;
import com.spring.giants.model.repository.BoardRepository;
import com.spring.giants.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@AllArgsConstructor
@RequestMapping("/board")
public class BoardController {


    final private BoardRepository boardRepository;
    // final private BoardValidator boardValidator;
    final private BoardService boardService;

    @GetMapping("/list")
    public String getList(Model model
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

}
