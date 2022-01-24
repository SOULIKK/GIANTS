package com.spring.giants.model.repository;

import com.spring.giants.model.dto.DisclosureResponseDto;
import com.spring.giants.model.entity.Board;
import com.spring.giants.model.entity.Disclosure;
import com.spring.giants.model.entity.Stock;
import com.spring.giants.model.repository.searchPageBoard.SearchPageBoardRepository;
import com.spring.giants.model.repository.searchPageDisclosure.SearchPageDisclosureRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface DisclosureRepository extends JpaRepository<Disclosure, String>, SearchPageDisclosureRepository, QuerydslPredicateExecutor<Disclosure> {

    List<DisclosureResponseDto> findTop10ByRceptDtOrderByCreatedAtDesc(Date rceptDt);

    DisclosureResponseDto findTop1ByOrderByRcpNoDesc();

    List<DisclosureResponseDto> findTop10ByStockCodeOrderByRcpNoDesc(String stockCode);

}
