package com.spring.giants.model.repository;

import com.spring.giants.model.dto.EpDto;
import com.spring.giants.model.entity.EditorsPick;
import com.spring.giants.model.repository.searchPageEp.SearchPageEpRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface EditorsPickRepository extends JpaRepository<EditorsPick, Long>, SearchPageEpRepository, QuerydslPredicateExecutor<EditorsPick> {


    @Query("SELECT new com.spring.giants.model.dto.EpDto(e.epId, e.title, e.description, e.url, e.thumbnail, e.createdAt, b.bookMarkId, COUNT(c.commentId) AS countComments) " +
            "FROM EditorsPick e " +
            "LEFT OUTER JOIN BookMark b ON e.epId = b.editorsPick.epId " +
            "LEFT OUTER JOIN Comment c ON e.epId = c.editorsPick.epId " +
            "WHERE e.title LIKE %?#{escape([0])}% escape ?#{escapeCharacter()} GROUP BY e.epId ORDER BY e.createdAt DESC")
    Page<EpDto> getEpList(String searchText, Pageable pageable);

    List<EditorsPick> findTop10ByOrderByCreatedAtDesc();

    @Query("DELETE FROM BookMark b WHERE b.editorsPick.epId =:epId AND b.user.userId =:userId")
    void deleteBookMarkByUserIdAndEpId(@Param("userId") Long userId, @Param("epId") Long epId);


}
