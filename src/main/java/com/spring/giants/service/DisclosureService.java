package com.spring.giants.service;

import com.spring.giants.model.dto.DisclosureResponseDto;
import com.spring.giants.model.repository.DisclosureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class DisclosureService {

    final private DisclosureRepository disclosureRepository;

    public Page<DisclosureResponseDto> getTodayDisclosure(Pageable pageable) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String now_dt = format.format(now);

        return disclosureRepository.findByRceptDt(now_dt, pageable);


    }
}
