package com.spring.giants.service;

import com.spring.giants.exception.ApiRequestException;
import com.spring.giants.model.dto.EpDto;
import com.spring.giants.model.entity.BookMark;
import com.spring.giants.model.entity.EditorsPick;
import com.spring.giants.model.entity.User;
import com.spring.giants.model.repository.BookMarkRepository;
import com.spring.giants.model.repository.EditorsPickRepository;
import com.spring.giants.model.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@AllArgsConstructor
public class EditorsPickService {

    final private EditorsPickRepository editorsPickRepository;
    final private UserRepository userRepository;
    final private BookMarkRepository bookMarkRepository;

    @Transactional
    public Page<EpDto> getEpList(Pageable pageable, String s) {

        Page<EpDto> epList = editorsPickRepository.getEpList(s, pageable);
        return epList;
    }


    @Transactional
    public void setBookMark(String username, Long epId) {

        User user = userRepository.findByUsername(username);
        EditorsPick editorsPick = editorsPickRepository.findById(epId).orElseThrow(
                () -> new ApiRequestException("존재하지 않는 게시물입니다."));

        boolean isBookMarked = chkBookMark(user, editorsPick);

        if (!isBookMarked) {
            BookMark bookMark = new BookMark(user, editorsPick);
            bookMarkRepository.save(bookMark);
        } else {
            bookMarkRepository.deleteByUserAndEditorsPick(user, editorsPick);
        }
    }

    @Transactional
    public boolean chkBookMark(User user, EditorsPick editorsPick) {
        BookMark isBookMarked = bookMarkRepository.findByUserAndEditorsPick(user, editorsPick);
        if (isBookMarked == null) {
            return false;
        } else {
            return true;
        }
    }

}
