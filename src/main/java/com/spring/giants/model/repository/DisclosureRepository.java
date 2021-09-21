package com.spring.giants.model.repository;

import com.spring.giants.model.dto.DisclosureResponseDto;
import com.spring.giants.model.entity.Disclosure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public interface DisclosureRepository extends JpaRepository<Disclosure, String> {

    List<DisclosureResponseDto> findTop10ByRceptDtOrderByRceptDtDesc(Date rceptDt);

    List<DisclosureResponseDto> findTop10ByStockCodeOrderByRcpNoDesc(String stockCode);

    Page<DisclosureResponseDto> findByRceptDtAndReportNmContainingOrReportNmContainingOrReportNmContainingOrderByRceptDtDesc(Date rceptDt, String title1, String title2, String title3, Pageable pageable);

    Page<DisclosureResponseDto> findAllByStockCodeOrderByRcpNoDesc(String stockId, Pageable pageable);

    Page<DisclosureResponseDto> findByReportNmAndRceptDtBetweenOrderByRcpNoDesc(String searchText, Date searchStart, Date searchEnd, Pageable pageable);

    Page<DisclosureResponseDto> findByCorpNameAndRceptDtBetweenOrderByRcpNoDesc(String searchText, Date searchStart, Date searchEnd, Pageable pageable);

    Page<DisclosureResponseDto> findByRceptDt(Date rceptDt, Pageable pageable);



    DisclosureResponseDto findTop1ByOrderByRcpNoDesc();
}
