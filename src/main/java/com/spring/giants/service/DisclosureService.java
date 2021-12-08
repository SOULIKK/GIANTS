package com.spring.giants.service;

import com.spring.giants.model.dto.DisclosureResponseDto;
import com.spring.giants.model.dto.StockDto;
import com.spring.giants.model.entity.Disclosure;
import com.spring.giants.model.entity.Stock;
import com.spring.giants.model.repository.DisclosureRepository;
import com.spring.giants.model.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DisclosureService {

    final private DisclosureRepository disclosureRepository;
    final private StockRepository stockRepository;

    private LocalDateTime getToday() {

        LocalDateTime now = LocalDateTime.now();
        DisclosureResponseDto dto = disclosureRepository.findTop1ByOrderByRcpNoDesc();
        LocalDateTime recentRcepDt = dto.getRceptDt();

        if (!recentRcepDt.equals(now)) {
            return dto.getRceptDt();
        }

        return now;
    }

    // 메인페이지 미리보기
    public List<DisclosureResponseDto> getMainReport() {
        LocalDateTime rceptDt = getToday();
        return disclosureRepository.findTop10ByRceptDtOrderByCreatedAtDesc(rceptDt);
    }

    // 종목 메인페이지 미리보기
    public List<DisclosureResponseDto> getReport(String stockId) {
        StockDto stock = stockRepository.findByStockName(stockId);
        return disclosureRepository.findTop10ByStockCodeOrderByRcpNoDesc(stockId);
    }

    // 공시 카테고리
    public Page<DisclosureResponseDto> getTodayReports(String reportType, Pageable pageable) throws ParseException {

        LocalDateTime rceptDt = getToday();

        if (reportType.equals("regular")) {
            String title1 = "사업보고서";
            String title2 = "분기보고서";
            String title3 = "분기보고서";
            return getDisclosureResponseDtos(pageable, rceptDt, title1, title2, title3);
        } else if (reportType.equals("share")) {
            String title1 = "임원ㆍ주요주주특정증권등소유상황보고서";
            String title2 = "주식등의대량보유상황보고서";
            String title3 = "공개매수";
            return getDisclosureResponseDtos(pageable, rceptDt, title1, title2, title3);
        } else if (reportType.equals("contract")) {
            String title1 = "공급계약체결";
            String title2 = title1;
            String title3 = title1;
            return getDisclosureResponseDtos(pageable, rceptDt, title1, title2, title3);
        }
        return disclosureRepository.findByRceptDtOrderByRcpNoDesc(rceptDt, pageable);
    }

    private Page<DisclosureResponseDto> getDisclosureResponseDtos(Pageable pageable, LocalDateTime rceptDt, String title1, String title2, String title3) {
        List<Disclosure> disclosures = disclosureRepository.findByRceptDtAndReportNm(title1, title2, title3, rceptDt, pageable);
        return getDisclosureResponseDtos(pageable, disclosures);
    }

    private Page<DisclosureResponseDto> getDisclosureResponseDtos(Pageable pageable, List<Disclosure> disclosures) {
        List<DisclosureResponseDto> disclosureResponseDto = disclosures.stream()
                .map(DisclosureResponseDto::new)
                .collect(Collectors.toList());
        int start = (int) pageable.getOffset();
        int end = (start + pageable.getPageSize()) > disclosureResponseDto.size() ? disclosureResponseDto.size() : (start + pageable.getPageSize());
        return new PageImpl<>(disclosureResponseDto.subList(start, end), pageable, disclosureResponseDto.size());
    }

    // 공시 상세검색
    public Page<DisclosureResponseDto> getSearchedReports(String searchType, Date searchStart, Date searchEnd, String searchText, Pageable pageable) {
        if (searchType.equals("reportNm")) {
            return disclosureRepository.findByReportNmAndRceptDtBetweenOrderByRcpNoDesc(searchText, searchStart, searchEnd, pageable);
        }
        return disclosureRepository.findByCorpNameAndRceptDtBetweenOrderByRcpNoDesc(searchText, searchStart, searchEnd, pageable);
    }

    @Transactional
    public Page<DisclosureResponseDto> getStockReports(String stockId, String reportType, Pageable pageable) {
        if (reportType.equals("regular")) {
            String title1 = "사업보고서";
            String title2 = "반기보고서";
            String title3 = "분기보고서";
            List<Disclosure> disclosures = disclosureRepository.findByReportNm(title1, title2, title3, stockId, pageable);
            return getDisclosureResponseDtos(pageable, disclosures);
        } else if (reportType.equals("share")) {
            String title1 = "임원ㆍ주요주주특정증권등소유상황보고서";
            String title2 = "주식등의대량보유상황보고서";
            String title3 = "공개매수";
            List<Disclosure> disclosures = disclosureRepository.findByReportNm(title1, title2, title3, stockId, pageable);
            return getDisclosureResponseDtos(pageable, disclosures);
        } else if (reportType.equals("contract")) {
            String title1 = "공급계약체결";
            String title2 = title1;
            String title3 = title1;
            List<Disclosure> disclosures = disclosureRepository.findByReportNm(title1, title2, title3, stockId, pageable);
            return getDisclosureResponseDtos(pageable, disclosures);
        }
        return disclosureRepository.findAllByStockCodeOrderByRcpNoDesc(stockId, pageable);
    }


}
