package com.spring.giants.model.repository.searchPageBoard;

import com.spring.giants.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchPageBoardRepository {

    Page<Object[]> searchPageStockBoard(String boardType, User user, String stockId, String type, String keyword, Pageable pageable);

}
