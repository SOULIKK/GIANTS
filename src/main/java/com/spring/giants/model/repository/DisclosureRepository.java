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


    Page<DisclosureResponseDto> findByRceptDt(String now_dt, Pageable pageable);

    List<DisclosureResponseDto> findTop10ByRceptDtOrderByRceptDtDesc(String rceptDt);

    List<DisclosureResponseDto> findTop10ByStockCodeOrderByRcpNoDesc(String stockCode);

    Page<DisclosureResponseDto> findByRceptDtAndReportNmContainingOrReportNmContainingOrReportNmContainingOrderByRceptDtDesc(String rceptDt, String year, String half, String quarter, Pageable pageable);

    Page<DisclosureResponseDto> findByReportNmAndRceptDtOrderByRcpNoDesc(String reportNm, String rceptDt, Pageable pageable);

    Page<DisclosureResponseDto> findByCorpNameAndRceptDtOrderByRcpNoDesc(String corpName, String rceptDt, Pageable pageable);
}
