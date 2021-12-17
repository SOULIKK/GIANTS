package com.spring.giants.model.repository;

import com.spring.giants.model.dto.DisclosureResponseDto;
import com.spring.giants.model.entity.Disclosure;
import com.spring.giants.model.entity.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface DisclosureRepository extends JpaRepository<Disclosure, String> {

    List<DisclosureResponseDto> findTop10ByRceptDtOrderByCreatedAtDesc(LocalDateTime rceptDt);

    Page<DisclosureResponseDto> findAllByStockCodeOrderByRcpNoDesc(String stockId, Pageable pageable);

    Page<DisclosureResponseDto> findByReportNmAndRceptDtBetweenOrderByRcpNoDesc(String searchText, Date searchStart, Date searchEnd, Pageable pageable);

    Page<DisclosureResponseDto> findByCorpNameAndRceptDtBetweenOrderByRcpNoDesc(String searchText, Date searchStart, Date searchEnd, Pageable pageable);

    Page<DisclosureResponseDto> findByRceptDtOrderByRcpNoDesc(LocalDateTime rceptDt, Pageable pageable);

    DisclosureResponseDto findTop1ByOrderByRcpNoDesc();

    @Query("SELECT new com.spring.giants.model.dto.DisclosureResponseDto(d.rcpNo, d.corpCls, d.corpCode, d.corpName, d.flrNm, d.reportNm, d.rceptDt, d.rm, d.stockCode) FROM Disclosure d WHERE (d.reportNm LIKE %:title1% OR d.reportNm LIKE %:title2% OR d.reportNm LIKE %:title3%) AND d.rceptDt = :rceptDt ORDER BY d.rcpNo DESC")
    Page<DisclosureResponseDto> findByRceptDtAndReportNm(@Param("title1") String title1, @Param("title2") String title2, @Param("title3") String title3, @Param("rceptDt") LocalDateTime rceptDt, Pageable pageable);

    @Query("SELECT new com.spring.giants.model.dto.DisclosureResponseDto(d.rcpNo, d.corpCls, d.corpCode, d.corpName, d.flrNm, d.reportNm, d.rceptDt, d.rm, d.stockCode) FROM Disclosure d WHERE (d.reportNm LIKE %:title1% OR d.reportNm LIKE %:title2% OR d.reportNm LIKE %:title3%) AND d.stockCode = :stockId ORDER BY d.rcpNo DESC")
    Page<DisclosureResponseDto> findByReportNm(@Param("title1") String title1, @Param("title2") String title2, @Param("title3") String title3, @Param("stockId") String stockId, Pageable pageable);

    List<DisclosureResponseDto> findTop10ByStockCodeOrderByRcpNoDesc(String stockCode);
}
