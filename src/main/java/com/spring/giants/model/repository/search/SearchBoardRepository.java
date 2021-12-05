package com.spring.giants.model.repository.search;

import com.spring.giants.model.entity.Board;
import com.spring.giants.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchBoardRepository {
//    Board search();

    Page<Object[]> searchPageStockBoard(String boardType, User user, String stockId, String type, String keyword, Pageable pageable);

}
