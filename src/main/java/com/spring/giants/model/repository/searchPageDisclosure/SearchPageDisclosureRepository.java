package com.spring.giants.model.repository.searchPageDisclosure;

import com.spring.giants.model.dto.PageRequestDto;
import com.spring.giants.model.entity.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Date;


public interface SearchPageDisclosureRepository {
    Page<Object[]> searchPageDisclosure(String disclosureType, Date rceptDt, LocalDate searchStart, LocalDate searchEnd, String type, String keyword, Pageable pageable);

    Page<Object[]> searchPageStockDisclosure(String stockId, String disclosureType, LocalDate searchStart, LocalDate searchEnd, PageRequestDto pageRequestDto, String keyword, Pageable createdAt);
}
