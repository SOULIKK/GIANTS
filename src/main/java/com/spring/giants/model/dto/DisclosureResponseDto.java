package com.spring.giants.model.dto;


import com.querydsl.core.annotations.QueryProjection;
import com.spring.giants.model.entity.Disclosure;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class DisclosureResponseDto {

    private String rcpNo;
    private String corpCls;
    private String corpCode;
    private String corpName;
    private String flrNm;
    private String reportNm;
    private Date rceptDt;
    private String rm;
    private String stockCode;
    private int commentCount;


    public DisclosureResponseDto(Disclosure disclosure) {
        this.rcpNo = disclosure.getRcpNo();
        this.corpCls = disclosure.getCorpCls();
        this.corpCode = disclosure.getCorpCode();
        this.corpName = disclosure.getCorpName();
        this.flrNm = disclosure.getFlrNm();
        this.reportNm = disclosure.getReportNm();
        this.rceptDt = disclosure.getRceptDt();
        this.rm = disclosure.getRm();
        this.stockCode = disclosure.getStockCode();
    }



}
