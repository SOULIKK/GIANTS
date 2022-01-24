package com.spring.giants.model.repository.searchPageDisclosure;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.spring.giants.model.dto.PageRequestDto;
import com.spring.giants.model.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


public class SearchPageDisclosureRepositoryImpl extends QuerydslRepositorySupport implements SearchPageDisclosureRepository {

    public SearchPageDisclosureRepositoryImpl() {
        super(Disclosure.class);
    }

    @Override
    public Page<Object[]> searchPageDisclosure(String disclosureType, java.util.Date rceptDt, LocalDate searchStart, LocalDate searchEnd, String type, String keyword, Pageable pageable) {

        QDisclosure disclosure = QDisclosure.disclosure;
        QComment comment = QComment.comment;

        JPQLQuery<Disclosure> jpqlQuery = from(disclosure);
        JPQLQuery<Tuple> tuple = jpqlQuery.select(
                disclosure,
                JPAExpressions.select(comment.count().as("commentCount")).from(comment).where(comment.disclosure.eq(disclosure))
        );

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = disclosure.rcpNo.isNotEmpty();
        booleanBuilder.and(expression);

        if (disclosureType.equals("regular")) {
            BooleanExpression exRegular12 = disclosure.reportNm.eq("사업보고서");
            BooleanExpression exRegular6 = disclosure.reportNm.eq("반기보고서");
            BooleanExpression exRegular3 = disclosure.reportNm.eq("분기보고서");
            booleanBuilder.and(exRegular3.or(exRegular6).or(exRegular12));
        }

        if (disclosureType.equals("share")) {
            BooleanExpression exShareMajorHolder = disclosure.reportNm.eq("임원ㆍ주요주주특정증권등소유상황보고서");
            BooleanExpression exShareBlock = disclosure.reportNm.eq("주식등의대량보유상황보고서");
            BooleanExpression exShareTenderOffer = disclosure.reportNm.eq("공개매수");
            booleanBuilder.and(exShareMajorHolder.or(exShareBlock).or(exShareTenderOffer));
        }

        if (disclosureType.equals("contract")) {
            BooleanExpression exContract = disclosure.reportNm.eq("공급계약체결");
            booleanBuilder.and(exContract);
        }


        if (searchStart == null && searchEnd == null) {
            BooleanExpression today = disclosure.rceptDt.eq(Date.valueOf(String.valueOf(rceptDt)));
            //BooleanExpression today = disclosure.rceptDt.eq(Date.valueOf("2022-01-04"));
            booleanBuilder.and(today);
            System.out.println("today ===================== " + rceptDt);
        }

        if (searchStart != null && searchEnd != null) {
            BooleanExpression period = disclosure.rceptDt.between(Date.valueOf(String.valueOf(searchStart)), Date.valueOf(String.valueOf(searchEnd)));
            booleanBuilder.and(period);
            System.out.println("searchStart ============ " + searchStart);
            System.out.println("searchEnd ============ " + searchEnd);
        }

        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if (type != null) {
            if (type.equals("t")) {
                conditionBuilder.or(disclosure.reportNm.contains(keyword));
                booleanBuilder.and(conditionBuilder);
            } else if (type.equals("c")) {
                conditionBuilder.or(disclosure.corpName.contains(keyword));
                booleanBuilder.and(conditionBuilder);
            }
        }


        tuple.where(booleanBuilder);
        Sort sort = pageable.getSort();

        sort.stream().forEach(order -> {
            tuple.orderBy(disclosure.createdAt.desc());
        });
        tuple.groupBy(disclosure);

        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();
        Long count = tuple.fetchCount();

        return new PageImpl<Object[]>(result.stream().map(t->t.toArray()).collect(Collectors.toList()), pageable, count);
    }

    @Override
    public Page<Object[]> searchPageStockDisclosure(String stockId, String disclosureType, LocalDate searchStart, LocalDate searchEnd, PageRequestDto pageRequestDto, String keyword, Pageable pageable) {
        QDisclosure disclosure = QDisclosure.disclosure;
        QComment comment = QComment.comment;
        QStock stock = QStock.stock;

        JPQLQuery<Disclosure> jpqlQuery = from(disclosure);
        jpqlQuery.innerJoin(stock).on(disclosure.stockCode.eq(stockId));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(
                disclosure,
                JPAExpressions.select(comment.count().as("commentCount")).from(comment).where(comment.disclosure.eq(disclosure))
        );


        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = disclosure.rcpNo.isNotEmpty();
        booleanBuilder.and(expression);

        if (disclosureType.equals("regular")) {
            BooleanExpression exRegular12 = disclosure.reportNm.eq("사업보고서");
            BooleanExpression exRegular6 = disclosure.reportNm.eq("반기보고서");
            BooleanExpression exRegular3 = disclosure.reportNm.eq("분기보고서");
            booleanBuilder.and(exRegular3.or(exRegular6).or(exRegular12));
        }

        if (disclosureType.equals("share")) {
            BooleanExpression exShareMajorHolder = disclosure.reportNm.eq("임원ㆍ주요주주특정증권등소유상황보고서");
            BooleanExpression exShareBlock = disclosure.reportNm.eq("주식등의대량보유상황보고서");
            BooleanExpression exShareTenderOffer = disclosure.reportNm.eq("공개매수");
            booleanBuilder.and(exShareMajorHolder.or(exShareBlock).or(exShareTenderOffer));
        }

        if (disclosureType.equals("contract")) {
            BooleanExpression exContract = disclosure.reportNm.eq("공급계약체결");
            booleanBuilder.and(exContract);
        }

        if (keyword != null) {
            BooleanBuilder conditionBuilder = new BooleanBuilder();
            conditionBuilder.or(disclosure.corpName.contains(keyword));
            booleanBuilder.and(conditionBuilder);
        }

        tuple.where(booleanBuilder);
        Sort sort = pageable.getSort();

        sort.stream().forEach(order -> {
            tuple.orderBy(disclosure.createdAt.desc());
        });

        tuple.groupBy(disclosure);
        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();
        Long count = tuple.fetchCount();

        return new PageImpl<Object[]>(result.stream().map(t->t.toArray()).collect(Collectors.toList()), pageable, count);
    }

}
