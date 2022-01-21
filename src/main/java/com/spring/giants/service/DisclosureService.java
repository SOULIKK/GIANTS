package com.spring.giants.service;
import com.spring.giants.model.dto.DisclosureResponseDto;
import com.spring.giants.model.dto.PageRequestDto;
import com.spring.giants.model.dto.PageResultDto;
import com.spring.giants.model.dto.StockDto;
import com.spring.giants.model.entity.Disclosure;
import com.spring.giants.model.entity.Stock;
import com.spring.giants.model.repository.DisclosureRepository;
import com.spring.giants.model.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.function.Function;


@RequiredArgsConstructor
@Service
public class DisclosureService {

    final private DisclosureRepository disclosureRepository;
    final private StockRepository stockRepository;

    private Date getToday() {

        Date now = new Date();
        DisclosureResponseDto dto = disclosureRepository.findTop1ByOrderByRcpNoDesc();
        Date recentRcepDt = dto.getRceptDt();
        if (!recentRcepDt.equals(now)) {
            return dto.getRceptDt();
        }
        return now;
    }

    // 메인페이지 미리보기
    public List<DisclosureResponseDto> getMainReport() {
        Date rceptDt = getToday();
        return disclosureRepository.findTop10ByRceptDtOrderByCreatedAtDesc(rceptDt);
    }

    // 종목 메인페이지 미리보기
    public List<DisclosureResponseDto> getReport(String stockId) {
        StockDto stock = stockRepository.findByStockName(stockId);
        return disclosureRepository.findTop10ByStockCodeOrderByRcpNoDesc(stockId);
    }

    public PageResultDto<DisclosureResponseDto, Object[]> getDislosureList(String disclosureType, LocalDate searchStart, LocalDate searchEnd, PageRequestDto pageRequestDto) {
        Date rceptDt = getToday();
        Function<Object[], DisclosureResponseDto> fn = (en -> entityToDto((Disclosure)en[0], (Long)en[1]));
        Page<Object[]> result = disclosureRepository.searchPageDisclosure(
                disclosureType,
                rceptDt,
                searchStart,
                searchEnd,
                pageRequestDto.getType(),
                pageRequestDto.getKeyword(),
                pageRequestDto.getPageable(Sort.by("createdAt").descending())
        );
        return new PageResultDto<>(result, fn);
    }

    private DisclosureResponseDto entityToDto(Disclosure disclosure, Long commentCount) {
        DisclosureResponseDto dto = DisclosureResponseDto.builder()
                .rcpNo(disclosure.getRcpNo())
                .corpCls(disclosure.getCorpCls())
                .corpName(disclosure.getCorpName())
                .flrNm(disclosure.getFlrNm())
                .reportNm(disclosure.getReportNm())
                .rceptDt(disclosure.getRceptDt())
                .rm(disclosure.getRm())
                .stockCode(disclosure.getStockCode())
                .commentCount(commentCount.intValue())
                .build();
        return dto;
    }

    public Object getStockDisclosure(String stockId, String disclosureType, LocalDate searchStart, LocalDate searchEnd, PageRequestDto pageRequestDto) {
        Function<Object[], DisclosureResponseDto> fn =(en -> entityToDto((Disclosure)en[0], (Long)en[1]));
        Page<Object[]> result = disclosureRepository.searchPageStockDisclosure(
                stockId,
                disclosureType,
                searchStart,
                searchEnd,
                pageRequestDto,
                pageRequestDto.getType(),
                pageRequestDto.getPageable(Sort.by("createdAt").descending())
        );
        return new PageResultDto<>(result, fn);
    }
}
