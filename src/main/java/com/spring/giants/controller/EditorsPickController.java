package com.spring.giants.controller;

import com.spring.giants.model.dto.BoardRequestDto;
import com.spring.giants.model.dto.EpDto;
import com.spring.giants.model.entity.EditorsPick;
import com.spring.giants.model.entity.User;
import com.spring.giants.service.BoardService;
import com.spring.giants.service.EditorsPickService;
import com.spring.giants.service.MainService;
import com.spring.giants.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class EditorsPickController {

    final private BoardService boardService;
    final private UserService userService;

    final private EditorsPickService editorsPickService;


    @GetMapping("/admin/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/admin/write")
    public String write() {
        return "admin/write";
    }

    @PostMapping("/admin/submit")
    public String submitEp(BoardRequestDto boardRequestDto, Authentication authentication) {
        String username = authentication.getName();
        boardService.setEpBoard(username, boardRequestDto);
        return "redirect:/main/pick";
    }

    @GetMapping("/pick")
    public String pickList(Pageable pageable, Model model) {

        Page<EpDto> epDto = editorsPickService.getList(pageable);

        int startPage = Math.max(1, epDto.getPageable().getPageNumber() - 4);
        int endPage = Math.min(epDto.getTotalPages(),epDto.getPageable().getPageNumber() + 4);

        model.addAttribute("picks", epDto);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "pick/list";
    }



}
