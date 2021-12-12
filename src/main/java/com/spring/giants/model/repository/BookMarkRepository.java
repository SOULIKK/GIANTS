package com.spring.giants.model.repository;


import com.spring.giants.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {

    BookMark findByUserAndEditorsPick(User user, EditorsPick editorsPick);

    void deleteByUserAndEditorsPick(User user, EditorsPick editorsPick);

}
