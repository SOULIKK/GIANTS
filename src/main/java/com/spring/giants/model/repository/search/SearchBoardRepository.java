package com.spring.giants.model.repository.search;

import com.spring.giants.model.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchBoardRepository {
    Board search();

    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);
}
