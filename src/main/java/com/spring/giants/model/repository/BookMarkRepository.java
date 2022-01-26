package com.spring.giants.model.repository;


import com.spring.giants.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {

    BookMark findByUserAndEditorsPick(User user, EditorsPick editorsPick);

    void deleteByUserAndEditorsPick(User user, EditorsPick editorsPick);

    @Query("SELECT b FROM BookMark b WHERE b.user =:userId AND b.editorsPick.epId =:epId")
    BookMark findByUserIdAndEpId(@Param("userId") Long userId, @Param("epId") Long epId);
}
