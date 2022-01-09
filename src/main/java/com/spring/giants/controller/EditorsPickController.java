package com.spring.giants.controller;

import com.spring.giants.model.dto.EpDto;
import com.spring.giants.service.BoardService;
import com.spring.giants.service.EditorsPickService;
import com.spring.giants.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


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


    @GetMapping("/pick")
    public String pickList(@PageableDefault(size = 8) Pageable pageable, @RequestParam(required = false, defaultValue = "") String s, Model model) {

        Page<EpDto> epDto = editorsPickService.getEpList(pageable, s);

        int startPage = Math.max(1, epDto.getPageable().getPageNumber() - 4);
        int endPage = Math.min(epDto.getTotalPages(),epDto.getPageable().getPageNumber() + 5);

        model.addAttribute("picks", epDto);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "pick/list";
    }

    @ResponseBody
    @PostMapping("/bookmark")
    public void bookMark(@RequestParam Long epId, Authentication authentication) {
        String username = authentication.getName();
        editorsPickService.setBookMark(username, epId);
    }



}
