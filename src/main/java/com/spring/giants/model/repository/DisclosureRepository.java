package com.spring.giants.model.repository;

import com.spring.giants.model.dto.DisclosureResponseDto;
import com.spring.giants.model.entity.Disclosure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Date;
import java.util.List;

public interface DisclosureRepository extends JpaRepository<Disclosure, String> {

    List<DisclosureResponseDto> findTop10ByRceptDtOrderByRceptDtDesc(Date rceptDt);

    List<DisclosureResponseDto> findTop10ByStockCodeOrderByRcpNoDesc(String stockCode);

    Page<DisclosureResponseDto> findAllByStockCodeOrderByRcpNoDesc(String stockId, Pageable pageable);

    Page<DisclosureResponseDto> findByReportNmAndRceptDtBetweenOrderByRcpNoDesc(String searchText, Date searchStart, Date searchEnd, Pageable pageable);

    Page<DisclosureResponseDto> findByCorpNameAndRceptDtBetweenOrderByRcpNoDesc(String searchText, Date searchStart, Date searchEnd, Pageable pageable);

    Page<DisclosureResponseDto> findByRceptDtOrderByRcpNoDesc(Date rceptDt, Pageable pageable);

    DisclosureResponseDto findTop1ByOrderByRcpNoDesc();

    @Query(value = "SELECT d.* FROM Disclosure d WHERE (report_nm LIKE %:title1% OR report_nm LIKE %:title2% OR report_nm LIKE %:title3%) AND rcept_dt = :rceptDt ORDER BY rcp_no DESC", nativeQuery = true)
    List<Disclosure> findByRceptDtAndReportNm(@Param("title1") String title1, @Param("title2") String title2, @Param("title3") String title3, @Param("rceptDt") Date rceptDt, Pageable pageable);

    @Query(value = "SELECT d.* FROM Disclosure d WHERE (report_nm LIKE %:title1% OR report_nm LIKE %:title2% OR report_nm LIKE %:title3%) AND stock_code = :stockId ORDER BY rcp_no DESC", nativeQuery = true)
    List<Disclosure> findByReportNm(@Param("title1") String title1, @Param("title2") String title2, @Param("title3") String title3, @Param("stockId") String stockId, Pageable pageable);



}
