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

    // 메인페이지 미리보기
    public List<DisclosureResponseDto> getMainReport() {
        //String rceptDt = getToday();
        String rceptDt = "20210917";
        return disclosureRepository.findTop10ByRceptDtOrderByRceptDtDesc(rceptDt);
    }

    private String getToday() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date now = new Date();
        String today = format.format(now);
        return today;
    }

    // 종목 메인페이지 미리보기
    public List<DisclosureResponseDto> getReport(String stockId) {
        return disclosureRepository.findTop10ByStockCodeOrderByRcpNoDesc(stockId);
    }


    public Page<DisclosureResponseDto> getTodayReports(String reportType, Pageable pageable) {

        // String rceptDt = getToday();
        String rceptDt = "20210917";

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

    public Page<DisclosureResponseDto> getSearchedReports(String searchType, String searchText, Pageable pageable) {
        //String today = getToday();
        String today = "20210917";
        if (searchType.equals("reportNm")) {
            return disclosureRepository.findByReportNmAndRceptDtOrderByRcpNoDesc(searchText, today, pageable);
        }
        return disclosureRepository.findByCorpNameAndRceptDtOrderByRcpNoDesc(searchText, today, pageable);
    }
}
