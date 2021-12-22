package com.spring.giants.service;


import com.spring.giants.model.dto.*;

import com.spring.giants.model.entity.BookMark;
import com.spring.giants.model.entity.EditorsPick;
import com.spring.giants.model.entity.User;
import com.spring.giants.model.entity.UserRole;
import com.spring.giants.model.repository.EditorsPickRepository;
import com.spring.giants.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class UserService {

    final private UserRepository userRepository;
    final private PasswordEncoder passwordEncoder;

    final private EditorsPickRepository editorsPickRepository;

    @Transactional
    public User join(User user) {
        String encodedPw = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPw);
        user.setEnabled(true);
        user.setRole(UserRole.USER);

        return userRepository.save(user);
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }


    @Transactional
    public void updatePw(String username, String password) {

        User user = userRepository.findByUsername(username);
        ProfileRequestDto profileRequestDto = new ProfileRequestDto();

        String encodedPw = passwordEncoder.encode(password);
        profileRequestDto.setPassword(encodedPw);

        user.update(profileRequestDto);
    }


    // my bookmark
    public PageResultDto<MyBookMarksDto, Object[]> getMyBookMark(String username, PageRequestDto pageRequestDto) {
        User user = userRepository.findByUsername(username);
        Function<Object[], MyBookMarksDto> fn = (en -> entityToDto((EditorsPick)en[0] ));

        Page<Object[]> result = editorsPickRepository.myBookmark(
                user,
                pageRequestDto.getType(),
                pageRequestDto.getKeyword(),
                pageRequestDto.getPageable(Sort.by("epId").descending())
        );

        return new PageResultDto<>(result, fn);
    }

    private MyBookMarksDto entityToDto(EditorsPick editorsPick) {
        MyBookMarksDto myBookMarksDto = MyBookMarksDto.builder()
                .epId(editorsPick.getEpId())
                .title(editorsPick.getTitle())
                .description(editorsPick.getDescription())
                .build();
        return myBookMarksDto;
    }


}
