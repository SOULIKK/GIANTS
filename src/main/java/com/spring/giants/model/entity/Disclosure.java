package com.spring.giants.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class Disclosure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long disclosureId;

    private String corpCode;
    private String corpName;
    private String stockCode;
    private String corpCls;
    private String reportNm;
    private String rceptNo;
    private String flrNm;
    private Date rceptDt;
    private String rm;

    // corp_code: "00120182",
    // corp_name: "NH투자증권",
    // stock_code: "005940",
    // corp_cls: "Y",
    // report_nm: "[첨부추가]일괄신고추가서류(파생결합증권-주가연계증권)",
    // rcept_no: "20200117000559",
    // flr_nm: "NH투자증권",
    // rcept_dt: "20200117",
    // rm: ""
}
