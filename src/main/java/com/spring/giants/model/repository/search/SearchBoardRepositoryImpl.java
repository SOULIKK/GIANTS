package com.spring.giants.model.repository.search;

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


public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {


    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }


    @Override
    public Board search() {
        QBoard board = QBoard.board;
        QComment comment = QComment.comment;
        QUser user = QUser.user;
        QLikes likes = QLikes.likes;

        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(user).on(board.user.eq(user));
        jpqlQuery.leftJoin(comment).on(comment.board.eq(board));
        jpqlQuery.leftJoin(likes).on(likes.board.eq(board));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, user, comment.count(), likes.count());
        tuple.groupBy(board);

        List<Tuple> result = tuple.fetch();

        return null;
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {

        QBoard board = QBoard.board;
        QComment comment = QComment.comment;

        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(comment).on(comment.board.eq(board));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, board.user, comment.count());

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = board.boardId.gt(0L);

        booleanBuilder.and(expression);

        if (type != null) {
            String[] typeArr = type.split("");
            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for (String t : typeArr) {
                switch (t) {
                    case "t":
                        conditionBuilder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        conditionBuilder.or(board.content.contains(keyword));
                        break;
                }
            }
            booleanBuilder.and(conditionBuilder);
        }
        tuple.where(booleanBuilder);
        Sort sort = pageable.getSort();

        sort.stream().forEach(order -> {
            Order direction = order.isAscending()? Order.ASC: Order.DESC;
            String prop = order.getProperty();
            PathBuilder orderByExpression = new PathBuilder(Board.class, "board");
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });

        tuple.groupBy(board);

        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();
        Long count = tuple.fetchCount();

        return new PageImpl<Object[]>(result.stream().map(t->t.toArray()).collect(Collectors.toList()), pageable, count);
    }
}
