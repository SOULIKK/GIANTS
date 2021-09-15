package com.spring.giants.model.repository;

import com.spring.giants.model.dto.DisclosureResponseDto;
import com.spring.giants.model.entity.Disclosure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.text.SimpleDateFormat;
import java.util.Date;

public interface DisclosureRepository extends JpaRepository<Disclosure, String> {


    Page<DisclosureResponseDto> findByRceptDt(String now_dt, Pageable pageable);
}
