package com.spring.giants.model.repository;

import com.spring.giants.model.dto.EpDto;
import com.spring.giants.model.entity.EditorsPick;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface EditorsPickRepository extends JpaRepository<EditorsPick, Long> {

    Page<EpDto> findByTitleContainingOrderByCreatedAtDesc(String searchText, Pageable pageable);

}
