package com.spring.giants.model.repository.searchPageEp;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.spring.giants.model.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

public class SearchPageEpRepositoryImpl extends QuerydslRepositorySupport implements SearchPageEpRepository {


    public SearchPageEpRepositoryImpl() {
        super(EditorsPick.class);
    }

    @Override
    public Page<Object[]> myBookmark(User user, String type, String keyword, Pageable pageable) {

        QBookMark bookMark = QBookMark.bookMark;
        QEditorsPick editorsPick = QEditorsPick.editorsPick;
        QLikes likes = QLikes.likes;

        JPQLQuery<EditorsPick> jpqlQuery = from(editorsPick);
        jpqlQuery.leftJoin(bookMark).on(bookMark.editorsPick.eq(editorsPick));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(editorsPick, bookMark, editorsPick.count());

        BooleanBuilder booleanBUilder = new BooleanBuilder();
        BooleanExpression expression = bookMark.bookMarkId.gt(0L);
        booleanBUilder.and(expression);

        BooleanExpression expression2 = bookMark.user.eq(user);
        booleanBUilder.and(expression2);

        if (type != null) {
            String[] typeArr = type.split("");
            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for (String t : typeArr) {
                switch(t) {
                    case "t":
                        conditionBuilder.or(editorsPick.title.contains(keyword));
                        break;
                    case "c":
                        conditionBuilder.or(editorsPick.description.contains(keyword));
                        break;
                }
            }
            booleanBUilder.and(conditionBuilder);
        }
        tuple.where(booleanBUilder);
        Sort sort = pageable.getSort();
        sort.stream().forEach(order -> {
            Order direction = order.isAscending()? Order.ASC: Order.DESC;
            String prop = order.getProperty();
            PathBuilder orderByExpression = new PathBuilder(EditorsPick.class, "editorsPick");
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });
        tuple.groupBy(editorsPick);
        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();
        Long count = tuple.fetchCount();

        return new PageImpl<Object[]>(result.stream().map(t->t.toArray()).collect(Collectors.toList()), pageable, count);
    }


}
