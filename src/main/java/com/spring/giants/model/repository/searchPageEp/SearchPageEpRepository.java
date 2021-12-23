package com.spring.giants.model.repository.searchPageEp;

import com.spring.giants.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchPageEpRepository {

    Page<Object[]> myBookmark(User user, String type, String keyword, Pageable pageable);

}
