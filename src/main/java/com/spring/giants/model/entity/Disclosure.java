package com.spring.giants.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class Disclosure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String rcpNo;
    private String corpCls;
    private String corpCode;
    private String corpName;
    private String flrNm;
    private String reportNm;
    private Date rceptDt;
    private String rm;
    private String stockCode;

}
