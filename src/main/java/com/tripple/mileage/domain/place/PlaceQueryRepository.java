package com.tripple.mileage.domain.place;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tripple.mileage.common.type.YnType;
import com.tripple.mileage.domain.review.QReview;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PlaceQueryRepository {

    private final QPlace place = QPlace.place;
    private final QReview review = QReview.review;

    private final JPAQueryFactory jpaQueryFactory;

    public Place findPlaceWithReviews(UUID placeId){
        Place result =  jpaQueryFactory.selectFrom(place)
                .join(place.reviews, review).fetchJoin()
                .where(place.placeId.eq(placeId))
                .where(review.useYn.eq(YnType.Y))
                .fetchAll().fetchOne();
        return result;
    }
}
