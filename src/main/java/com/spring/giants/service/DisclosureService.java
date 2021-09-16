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

    public Page<DisclosureResponseDto> getTodayDisclosure(Pageable pageable) {
        String now_dt = getToday();
        return disclosureRepository.findByRceptDt(now_dt, pageable);

    }

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

    public List<DisclosureResponseDto> getReport(String stockId) {
        return disclosureRepository.findTop10ByStockCodeOrderByRcpNoDesc(stockId);
    }
}
