package com.spring.giants.model.repository.searchPageDisclosure;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.spring.giants.model.entity.Disclosure;
import com.spring.giants.model.entity.QComment;
import com.spring.giants.model.entity.QDisclosure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class SearchPageRepositoryImpl extends QuerydslRepositorySupport implements SearchPageDisclosureRepository {

    public SearchPageRepositoryImpl() {
        super(Disclosure.class);
    }

    @Override
    public Page<Object[]> searchPageDisclosure(String disclosureType, Date rceptDt, String type, String keyword, Pageable pageable) {

        QDisclosure disclosure = QDisclosure.disclosure;
        QComment comment = QComment.comment;

        JPQLQuery<Disclosure> jpqlQuery = from(disclosure);
        JPQLQuery<Tuple> tuple = jpqlQuery.select(
                disclosure,
                JPAExpressions.select(comment.count().as("countComment")).from(comment).where(comment.disclosure.eq(disclosure))
        );

        BooleanBuilder booleanBuilder = new BooleanBuilder();


        if (disclosureType == "D_REGULAR") {
            BooleanExpression exRegular12 = disclosure.reportNm.eq("사업보고서");
            BooleanExpression exRegular6 = disclosure.reportNm.eq("반기보고서");
            BooleanExpression exRegular3 = disclosure.reportNm.eq("분기보고서");
            booleanBuilder.and(exRegular3.or(exRegular6).or(exRegular12));
        }

        if (disclosureType == "D_SHARE") {
            BooleanExpression exShareMajorHolder = disclosure.reportNm.eq("임원ㆍ주요주주특정증권등소유상황보고서");
            BooleanExpression exShareBlock = disclosure.reportNm.eq("주식등의대량보유상황보고서");
            BooleanExpression exShareTenderOffer = disclosure.reportNm.eq("공개매수");
            booleanBuilder.and(exShareMajorHolder.or(exShareBlock).or(exShareTenderOffer));
        }

        if (disclosureType == "D_CONTRACT") {
            BooleanExpression exContract = disclosure.reportNm.eq("공급계약체결");
            booleanBuilder.and(exContract);
        }

        tuple.where(booleanBuilder);
        Sort sort = pageable.getSort();
        sort.stream().forEach(order -> {
            Order direction = order.isAscending()? Order.ASC: Order.DESC;
            tuple.orderBy(disclosure.createdAt.desc());
        });

        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();
        Long count = tuple.fetchCount();

        return new PageImpl<Object[]>(result.stream().map(t->t.toArray()).collect(Collectors.toList()), pageable, count);
    }
}
