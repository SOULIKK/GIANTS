package com.spring.giants.service;

import com.spring.giants.model.dto.EpDto;
import com.spring.giants.model.entity.EditorsPick;
import com.spring.giants.model.repository.EditorsPickRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@AllArgsConstructor
public class EditorsPickService {

    final private EditorsPickRepository editorsPickRepository;

    @Transactional
    public Page<EpDto> getList(Pageable pageable) {
        Page<EpDto> epList = editorsPickRepository.getEpList(pageable);
        return epList;
    }
}
