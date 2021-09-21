package com.spring.giants.service;

import com.spring.giants.model.dto.DisclosureResponseDto;
import com.spring.giants.model.repository.DisclosureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DisclosureService {

    final private DisclosureRepository disclosureRepository;

    private Date getToday() {
        Date now = new Date();
        DisclosureResponseDto dto = disclosureRepository.findTop1ByOrderByRcpNoDesc();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(now);
        String recentRcepDt = format.format(dto.getRceptDt());

        if (!recentRcepDt.equals(today)) {
            return dto.getRceptDt();
        }
        return now;
    }

    // 메인페이지 미리보기
    public List<DisclosureResponseDto> getMainReport() {
        Date rceptDt = getToday();
        return disclosureRepository.findTop10ByRceptDtOrderByRceptDtDesc(rceptDt);
    }


    // 종목 메인페이지 미리보기
    public List<DisclosureResponseDto> getReport(String stockId) {
        return disclosureRepository.findTop10ByStockCodeOrderByRcpNoDesc(stockId);
    }

    // 공시 카테고리
    public Page<DisclosureResponseDto> getTodayReports(String reportType, Pageable pageable) {

         Date rceptDt = getToday();

        if (reportType.equals("regular")) {
            String title1 = "사업보고서";
            String title2 = "분기보고서";
            String title3 = "분기보고서";
            return disclosureRepository.findByRceptDtAndReportNmContainingOrReportNmContainingOrReportNmContainingOrderByRceptDtDesc(rceptDt, title1, title2, title3, pageable);
        } else if (reportType.equals("share")) {
            String title1 = "임원ㆍ주요주주특정증권등소유상황보고서";
            String title2 = "주식등의대량보유상황보고서";
            String title3 = "공개매수";
            return disclosureRepository.findByRceptDtAndReportNmContainingOrReportNmContainingOrReportNmContainingOrderByRceptDtDesc(rceptDt, title1, title2, title3, pageable);
        } else if (reportType.equals("contract")) {
            String title1 = "공급계약체결";
            String title2 = title1;
            String title3 = title1;
            return disclosureRepository.findByRceptDtAndReportNmContainingOrReportNmContainingOrReportNmContainingOrderByRceptDtDesc(rceptDt, title1, title2, title3, pageable);
        } else if (reportType.equals("today")) {
            String title1 = "";
            String title2 = "";
            String title3 = "";
            return disclosureRepository.findByRceptDtAndReportNmContainingOrReportNmContainingOrReportNmContainingOrderByRceptDtDesc(rceptDt, title1, title2, title3, pageable);
        }

        return disclosureRepository.findByRceptDt(rceptDt, pageable);
    }

    // 공시 상세검색
    public Page<DisclosureResponseDto>  getSearchedReports(String searchType, Date searchStart, Date searchEnd, String searchText, Pageable pageable) {
        if (searchType.equals("reportNm")) {
            return disclosureRepository.findByReportNmAndRceptDtBetweenOrderByRcpNoDesc(searchText, searchStart, searchEnd, pageable);
        }
        return disclosureRepository.findByCorpNameAndRceptDtBetweenOrderByRcpNoDesc(searchText, searchStart, searchEnd, pageable);
    }

    public Page<DisclosureResponseDto> getStockReports(String stockId, Pageable pageable) {
        return disclosureRepository.findAllByStockCodeOrderByRcpNoDesc(stockId, pageable);
    }
}
