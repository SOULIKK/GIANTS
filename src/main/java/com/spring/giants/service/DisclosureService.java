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
        String rceptDt = getToday();
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


    public Page<DisclosureResponseDto> getTodayReport(String reportType, Pageable pageable) {
        String now_dt = getToday();
        if (reportType.equals("regular")) {
            String year = "사업보고서";
            String half = "분기보고서";
            String quarter = "분기보고서";
            String rceptDt = getToday();
            return disclosureRepository.findByRceptDtAndReportNmContainingOrReportNmContainingOrReportNmContainingOrderByRceptDtDesc(rceptDt, year, half, quarter, pageable);
        }
        return disclosureRepository.findByRceptDt(now_dt, pageable);
    }
}
